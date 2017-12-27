package io.junq.examples.usercenter.web.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import io.junq.examples.usercenter.persistence.model.Role;

public class RoleResource extends ResourceSupport {

	private final Role role;
	
	public RoleResource(final Role role) {
		this.role = role;
		
		this.add(linkTo(RoleHateoasController.class).withRel("roles"));
		this.add(linkTo(
				methodOn(RoleHateoasController.class, role)
					.findOne(role.getId()))
				.withSelfRel());
	}
	
	public Role getRole() {
		return role;
	}
	
}
