package io.junq.examples.common.web;

import io.junq.examples.common.interfaces.IDto;

public interface IUriMapper {

    <T extends IDto> String getUriBase(final Class<T> clazz);

}
