package io.junq.examples.usercenter.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.junq.examples.common.interfaces.IByNameApi;
import io.junq.examples.usercenter.persistence.model.User;

public interface IUserJpaDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, IByNameApi<User> {
    //
}