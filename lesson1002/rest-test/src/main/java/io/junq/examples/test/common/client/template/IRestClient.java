package io.junq.examples.test.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import io.junq.examples.client.marshall.IMarshaller;
import io.junq.examples.client.template.IRestClientWithUri;
import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.common.interfaces.IOperations;
import io.junq.examples.common.search.ClientOperation;
import io.restassured.specification.RequestSpecification;

public interface IRestClient<T extends IDto> extends IOperations<T>, IRestClientAsResponse<T>, IRestClientWithUri<T> {
	
    // search

    List<T> searchPaginated(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);
	
    // template

    RequestSpecification givenReadAuthenticated();

    RequestSpecification givenDeleteAuthenticated();

    IMarshaller getMarshaller();

    String getUri();
}
