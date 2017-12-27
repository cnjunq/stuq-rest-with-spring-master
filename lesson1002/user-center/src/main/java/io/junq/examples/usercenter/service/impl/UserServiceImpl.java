package io.junq.examples.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.junq.examples.common.persistence.service.AbstractService;
import io.junq.examples.usercenter.persistence.dao.IUserJpaDao;
import io.junq.examples.usercenter.persistence.model.User;
import io.junq.examples.usercenter.service.IUserService;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Autowired
    private IUserJpaDao dao;

    public UserServiceImpl() {
        super(User.class);
    }

    // API

    // 查找

    @Override
    @Transactional(readOnly = true)
    public User findByName(final String name) {
        return dao.findByName(name);
    }

    // Spring

    @Override
    protected final IUserJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<User> getSpecificationExecutor() {
        return dao;
    }
}
