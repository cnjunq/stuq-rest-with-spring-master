package io.junq.examples.common.persistence.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.junq.examples.common.persistence.ServicePreconditions;
import io.junq.examples.common.persistence.model.IEntity;

@Transactional
public abstract class AbstractRawService<T extends IEntity> implements IRawService<T> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected abstract PagingAndSortingRepository<T, Long> getDao();

	protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

	public AbstractRawService() {
		super();
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

	// 排序模板

	protected final Sort constructSort(final String sortBy, final String sortOrder) {
		Sort sortInfo = null;
		if (sortBy != null) {
			sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
		}
		return sortInfo;
	}
}
