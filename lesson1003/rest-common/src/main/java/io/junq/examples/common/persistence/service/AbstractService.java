package io.junq.examples.common.persistence.service;

import io.junq.examples.common.persistence.model.INameableEntity;

public abstract class AbstractService<T extends INameableEntity> extends AbstractRawService<T> implements IService<T> {
	
    public AbstractService(final Class<T> clazzToSet) {
        super(clazzToSet);
    }
    
}
