package io.junq.examples.common.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.junq.examples.common.persistence.ServicePreconditions;
import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.search.ClientOperation;
import io.junq.examples.common.util.SearchCommonUtil;
import io.junq.examples.common.web.exception.IJBadRequestException;
import io.junq.examples.common.web.exception.IJConflictException;

@Transactional
public abstract class AbstractRawService<T extends IEntity> implements IRawService<T> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private Class<T> clazz;
	
	protected abstract PagingAndSortingRepository<T, Long> getDao();

	protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

	public AbstractRawService(final Class<T> clazzToSet) {
		super();
		
		clazz = clazzToSet;
	}
	
	// search

    @SuppressWarnings("unchecked")
    @Override
    public List<T> searchAll(final String queryString) {
        Preconditions.checkNotNull(queryString);
        List<Triple<String, ClientOperation, String>> parsedQuery = null;
        try {
            parsedQuery = SearchCommonUtil.parseQueryString(queryString);
        } catch (final IllegalStateException illState) {
        	LOGGER.error("IllegalStateException on find operation");
        	LOGGER.warn("IllegalStateException on find operation", illState);
            throw new IJBadRequestException(illState);
        }

        final List<T> results = searchAll(parsedQuery.toArray(new ImmutableTriple[parsedQuery.size()]));
        return results;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public List<T> searchPaginated(final String queryString, final int page, final int size) {
        List<Triple<String, ClientOperation, String>> parsedQuery = null;
        try {
            parsedQuery = SearchCommonUtil.parseQueryString(queryString);
        } catch (final IllegalStateException illState) {
            LOGGER.error("IllegalStateException on find operation");
            LOGGER.warn("IllegalStateException on find operation", illState);
            throw new IJConflictException(illState);
        }

        final Page<T> resultPage = searchPaginated(page, size, parsedQuery.toArray(new ImmutableTriple[parsedQuery.size()]));
        return Lists.newArrayList(resultPage.getContent());
    }

    @Override
    public List<T> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        Preconditions.checkState(constraints != null);
        Preconditions.checkState(constraints.length > 0);
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }
        if (firstSpec == null) {
            return Lists.newArrayList();
        }

        return getSpecificationExecutor().findAll(specifications);
    }

    @Override
    public T searchOne(final Triple<String, ClientOperation, String>... constraints) {
        Preconditions.checkState(constraints != null);
        Preconditions.checkState(constraints.length > 0);
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }
        if (firstSpec == null) {
            return null;
        }

        return getSpecificationExecutor().findOne(specifications);
    }

    @Override
    public Page<T> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints) {
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Preconditions.checkState(firstSpec != null);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }

        return getSpecificationExecutor().findAll(specifications, new PageRequest(page, size, null));
    }

	@Transactional(readOnly = true)
	public T findOne(final long id) {
		return getDao().findOne(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return Lists.newArrayList(getDao().findAll());
	}

	@Transactional(readOnly = true)
	public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy,
			final String sortOrder) {
		final Sort sortInfo = constructSort(sortBy, sortOrder);
		return getDao().findAll(new PageRequest(page, size, sortInfo));
	}

	@Transactional(readOnly = true)
	public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy,
			final String sortOrder) {
		final Sort sortInfo = constructSort(sortBy, sortOrder);
		final List<T> content = getDao().findAll(new PageRequest(page, size, sortInfo)).getContent();
		if (content == null) {
			return Lists.newArrayList();
		}
		return content;
	}

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedRaw(final int page, final int size) {       
        return getDao().findAll(new PageRequest(page, size));
    }
	
	@Transactional(readOnly = true)
	public List<T> findAllPaginated(final int page, final int size) {
		final List<T> content = getDao().findAll(new PageRequest(page, size, null)).getContent();
		if (content == null) {
			return Lists.newArrayList();
		}
		return content;
	}

	@Transactional(readOnly = true)
	public List<T> findAllSorted(final String sortBy, final String sortOrder) {
		final Sort sortInfo = constructSort(sortBy, sortOrder);
		return Lists.newArrayList(getDao().findAll(sortInfo));
	}

	// 新建并保存

	public T create(final T entity) {
		Preconditions.checkNotNull(entity);

		final T persistedEntity = getDao().save(entity);

		return persistedEntity;
	}
	
	// 更新操作

	public void update(final T entity) {
		Preconditions.checkNotNull(entity);

		getDao().save(entity);
	}

	// 删除操作

	public void deleteAll() {
		getDao().deleteAll();
	}

	public void delete(final long id) {
		final T entity = getDao().findOne(id);
		ServicePreconditions.checkEntityExists(entity);

		getDao().delete(entity);
	}

	// count

	public long count() {
		return getDao().count();
	}
	

    // template method

    @SuppressWarnings({ "static-method", "unused" })
    public Specification<T> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        throw new UnsupportedOperationException();
    }

	// 排序模板

	protected final Sort constructSort(final String sortBy, final String sortOrder) {
		Sort sortInfo = null;
		if (sortBy != null) {
			sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
		}
		return sortInfo;
	}
}
