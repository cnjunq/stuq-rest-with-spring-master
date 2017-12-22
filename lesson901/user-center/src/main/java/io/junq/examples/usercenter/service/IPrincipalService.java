package io.junq.examples.usercenter.service;

import io.junq.examples.common.persistence.service.IService;
import io.junq.examples.usercenter.persistence.model.Principal;

public interface IPrincipalService extends IService<Principal> {
	
	Principal getCurrentPrincipal();
	
}
