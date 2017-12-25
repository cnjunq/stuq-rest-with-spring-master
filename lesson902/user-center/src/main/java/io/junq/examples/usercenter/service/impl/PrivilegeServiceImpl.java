package io.junq.examples.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.junq.examples.common.persistence.service.AbstractService;
import io.junq.examples.usercenter.persistence.dao.IPrivilegeJpaDao;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.service.IPrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    @Autowired
    private IPrivilegeJpaDao dao;

    public PrivilegeServiceImpl() {
        super();
    }

    // API

    // 查询

    @Override
    public Privilege findByName(final String name) {
    	return getDao().findByName(name);
    }

    // Spring

    @Override
    protected final IPrivilegeJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
