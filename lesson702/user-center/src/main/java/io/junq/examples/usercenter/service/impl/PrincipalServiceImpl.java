package io.junq.examples.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.junq.examples.common.persistence.service.AbstractService;
import io.junq.examples.common.security.SpringSecurityUtil;
import io.junq.examples.usercenter.persistence.dao.IPrincipalJpaDao;
import io.junq.examples.usercenter.persistence.model.Principal;
import io.junq.examples.usercenter.service.IPrincipalService;

@Service
@Transactional
public class PrincipalServiceImpl extends AbstractService<Principal> implements IPrincipalService {
	
	   @Autowired
	    private IPrincipalJpaDao dao;

	    public PrincipalServiceImpl() {
	        super();
	    }

	    // API

	    // find

	    @Override
	    @Transactional(readOnly = true)
	    public Principal findByName(final String name) {
	        return dao.findByName(name);
	    }

	    // other

	    @Override
	    @Transactional(readOnly = true)
	    public Principal getCurrentPrincipal() {
	        final String principalName = SpringSecurityUtil.getNameOfCurrentPrincipal();
	        return getDao().findByName(principalName);
	    }

	    // Spring

	    @Override
	    protected final IPrincipalJpaDao getDao() {
	        return dao;
	    }

	    @Override
	    protected JpaSpecificationExecutor<Principal> getSpecificationExecutor() {
	        return dao;
	    }
}
