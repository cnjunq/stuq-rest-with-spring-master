package io.junq.examples.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.junq.examples.common.persistence.service.AbstractService;
import io.junq.examples.usercenter.persistence.dao.IRoleJpaDao;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    @Autowired
    private IRoleJpaDao dao;

    public RoleServiceImpl() {
        super(Role.class);
    }

    // API

    // 查找

    @Override
    public Role findByName(final String name) {
        return getDao().findByName(name);
    }

    // 新建

    @Override
    public Role create(final Role entity) {
        /*
         * final long id = IdUtil.randomPositiveLong(); entity.setId( id );
         */

        /*
         * final List< Privilege > associationsTemp = Lists.newArrayList( entity.getPrivileges() ); entity.getPrivileges().clear(); for( final Privilege privilege : associationsTemp ){ entity.getPrivileges().add(
         * associationDao.findByName( privilege.getName() ) ); }
         */

        return super.create(entity);
    }

    // Spring

    @Override
    protected final IRoleJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }

}
