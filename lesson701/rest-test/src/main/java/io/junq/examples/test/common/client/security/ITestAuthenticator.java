package io.junq.examples.test.common.client.security;

import io.restassured.specification.RequestSpecification;

public interface ITestAuthenticator {
	
    RequestSpecification givenAuthenticated(final String username, final String password);

}
