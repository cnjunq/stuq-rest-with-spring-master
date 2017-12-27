package io.junq.examples.usercenter.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.junq.examples.common.interfaces.IByNameApi;
import io.junq.examples.usercenter.persistence.model.Principal;

public interface IPrincipalJpaDao extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, IByNameApi<Principal> {
    
}
