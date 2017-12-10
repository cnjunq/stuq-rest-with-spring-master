package io.junq.examples.common.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.persistence.service.IRawService;
import io.junq.examples.common.util.QueryConstants;
import io.junq.examples.common.web.RestPreconditions;
import io.junq.examples.common.web.WebConstants;
import io.junq.examples.common.web.events.MultipleResourcesRetrievedEvent;
import io.junq.examples.common.web.events.PaginatedResultsRetrievedEvent;
import io.junq.examples.common.web.events.SingleResourceRetrievedEvent;
import io.junq.examples.common.web.exception.IJResourceNotFoundException;

public abstract class AbstractReadOnlyController <D extends IDto, E extends IEntity> {
	
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected Class<D> clazz;
    
    @Autowired
    protected ApplicationEventPublisher eventPublisher;
    
    public AbstractReadOnlyController(final Class<D> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }
    
    // 查找：一条记录

    protected final D findOneInternal(final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final D resource = findOneInternal(id);
        eventPublisher.publishEvent(new SingleResourceRetrievedEvent<D>(clazz, uriBuilder, response));
        return resource;
    }
    
    protected final D findOneInternal(final Long id) {
        return (D) RestPreconditions.checkNotNull(getService().findOne(id));
    }

    // 查找：所有记录
    
    protected final List<D> findAllInternal(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
    	// 如果URL中包含其它参数直接返回找不到资源，特例是如果要支持URL查询参数内容协商的话，需要放过对应参数。
        if (!request.getParameterMap().containsKey(QueryConstants.FORMAT) &&
        		request.getParameterNames().hasMoreElements()) {
            throw new IJResourceNotFoundException();
        }

        eventPublisher.publishEvent(new MultipleResourcesRetrievedEvent<D>(clazz, uriBuilder, response));
        return (List<D>) getService().findAll();
    }

    protected final void findAllRedirectToPagination(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String locationValue = uriBuilder.path(WebConstants.PATH_SEP + resourceName).build().encode().toUriString() + QueryConstants.QUESTIONMARK + "page=0&size=10";

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }
    
    protected final List<D> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new IJResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<D>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findPaginatedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new IJResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<D>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<D> resultPage = (List<D>) getService().findAllSorted(sortBy, sortOrder);
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

    protected abstract IRawService<E> getService();
}
