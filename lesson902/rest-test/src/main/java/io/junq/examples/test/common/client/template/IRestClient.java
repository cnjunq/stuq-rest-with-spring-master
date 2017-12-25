package io.junq.examples.test.common.client.template;

import io.junq.examples.client.marshall.IMarshaller;
import io.junq.examples.client.template.IRestClientWithUri;
import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.common.interfaces.IOperations;
import io.restassured.specification.RequestSpecification;

public interface IRestClient<T extends IDto> extends IOperations<T>, IRestClientAsResponse<T>, IRestClientWithUri<T> {
    // template

    RequestSpecification givenReadAuthenticated();

    RequestSpecification givenDeleteAuthenticated();

    IMarshaller getMarshaller();

    String getUri();
}
