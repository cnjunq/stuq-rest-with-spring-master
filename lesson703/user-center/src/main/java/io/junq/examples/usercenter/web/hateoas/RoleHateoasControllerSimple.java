package io.junq.examples.usercenter.web.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.service.IRoleService;
import io.junq.examples.usercenter.util.UserCenter.Privileges;
import io.junq.examples.usercenter.util.UserCenterMapping;

@Controller
//@RequestMapping(value = UserCenterMapping.Hateoas.ROLES)
public class RoleHateoasControllerSimple {
	
	@Autowired
	private IRoleService service;
	
	// API - 查找单条记录
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
	public RoleResource findOne(@PathVariable("id") final Long id) {
		final Role entity = service.findOne(id);
		return new RoleResource(entity);
	}
	
}
