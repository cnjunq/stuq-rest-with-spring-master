package io.junq.examples.common.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.web.RestPreconditions;
import io.junq.examples.common.web.events.AfterResourceCreatedEvent;

public abstract class AbstractController <T extends IEntity> extends AbstractReadOnlyController<T> {
	
    @Autowired
    public AbstractController(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

	// 新建并保存

    protected final void createInternal(final T resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        final T existingResource = getService().create(resource);

        eventPublisher.publishEvent(new AfterResourceCreatedEvent<T>(clazz, uriBuilder, response, existingResource.getId().toString()));
    }

	// 更新操作
    
    protected final void updateInternal(final long id, final T resource) {
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
