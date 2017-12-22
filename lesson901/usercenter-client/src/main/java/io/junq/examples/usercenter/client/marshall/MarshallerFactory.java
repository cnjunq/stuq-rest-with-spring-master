package io.junq.examples.usercenter.client.marshall;

import static io.junq.examples.common.spring.util.Profiles.PRODUCTION;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.junq.examples.client.marshall.IMarshaller;

@Component
@Profile(PRODUCTION)
public class MarshallerFactory implements FactoryBean<IMarshaller> {
	
	public MarshallerFactory() {
		super();
	}

	// API

	@Override
	public IMarshaller getObject() {
		return new ProdJacksonMarshaller();
	}

	@Override
	public Class<IMarshaller> getObjectType() {
		return IMarshaller.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
