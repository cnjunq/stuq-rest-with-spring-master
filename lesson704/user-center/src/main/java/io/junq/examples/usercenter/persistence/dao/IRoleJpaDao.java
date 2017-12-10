package io.junq.examples.usercenter.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.junq.examples.common.interfaces.IByNameApi;
import io.junq.examples.usercenter.persistence.model.Role;

public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {
    //
}
