package io.junq.examples.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.persistence.service.IRawService;
import io.junq.examples.common.util.QueryConstants;
import io.junq.examples.common.web.RestPreconditions;
import io.junq.examples.common.web.exception.IJResourceNotFoundException;

public abstract class AbstractReadOnlyController <T extends IEntity> {
	
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;
    
    @Autowired
    protected ApplicationEventPublisher eventPublisher;
    
    public AbstractReadOnlyController(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }
    
    // 查找：一条记录

    protected final T findOneInternal(final Long id) {
        return RestPreconditions.checkNotNull(getService().findOne(id));
    }

    // 查找：所有记录

    protected final List<T> findAllInternal(final HttpServletRequest request) {
    	// 如果URL中包含其它参数直接返回找不到资源，特例是如果要支持URL查询参数内容协商的话，需要放过对应参数。
        if (!request.getParameterMap().containsKey(QueryConstants.FORMAT) &&
        		request.getParameterNames().hasMoreElements()) {
            throw new IJResourceNotFoundException();
        }

        return getService().findAll();
    }

    protected final List<T> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<T> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new IJResourceNotFoundException();
        }

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findPaginatedInternal(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<T> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new IJResourceNotFoundException();
        }

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<T> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<T> resultPage = getService().findAllSorted(sortBy, sortOrder);
        return resultPage;
    }

    // count

    protected final long countInternal() {
        return getService().count();
    }

    // count

    /**
     * 获取系统内所有 {@link Privilege} 资源数量
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public long count() {
        return countInternal();
    }

    protected abstract IRawService<T> getService();
}
