package io.junq.examples.common.persistence.service;

import io.junq.examples.common.interfaces.IByNameApi;
import io.junq.examples.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

}
