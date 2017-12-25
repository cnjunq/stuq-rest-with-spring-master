package io.junq.examples.client.template;

import io.junq.examples.common.interfaces.IDto;

public interface IRestClientWithUri<T extends IDto> extends IReadOnlyTemplateWithUri<T> {
    // create

    String createAsUri(final T resource);
}
