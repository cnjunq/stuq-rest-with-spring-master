package io.junq.examples.usercenter.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.junq.examples.common.interfaces.IByNameApi;
import io.junq.examples.usercenter.persistence.model.Privilege;

public interface IPrivilegeJpaDao extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege>, IByNameApi<Privilege> {
    //
}
