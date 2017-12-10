package io.junq.examples.common.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.web.RestPreconditions;
import io.junq.examples.common.web.events.AfterResourceCreatedEvent;

public abstract class AbstractController <D extends IDto, E extends IEntity> extends AbstractReadOnlyController<D, E> {
	
    @Autowired
    public AbstractController(final Class<D> clazzToSet) {
        super(clazzToSet);
    }

	// 新建并保存

    protected final void createInternal(final E resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        final E existingResource = getService().create(resource);

        eventPublisher.publishEvent(new AfterResourceCreatedEvent<D>(clazz, uriBuilder, response, existingResource.getId().toString()));
    }

	// 更新操作
    
    protected final void updateInternal(final long id, final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkRequestState(resource.getId() == id);
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        getService().update(resource);
    }

	// 删除操作

    protected final void deleteByIdInternal(final long id) {
        getService().delete(id);
    }
}
