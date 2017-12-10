package io.junq.examples.usercenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import io.junq.examples.common.util.QueryConstants;
import io.junq.examples.common.web.controller.AbstractController;
import io.junq.examples.common.web.controller.ISortingController;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.service.IPrivilegeService;
import io.junq.examples.usercenter.util.UserCenter.Privileges;
import io.junq.examples.usercenter.util.UserCenterMapping;

@Controller
@RequestMapping(value = UserCenterMapping.PRIVILEGES)
public class PrivilegeRestController extends AbstractController<Privilege, Privilege> implements ISortingController<Privilege> {

	@Autowired
    private IPrivilegeService service;

    public PrivilegeRestController() {
        super(Privilege.class);
    }

    // API

    // 查找：所有、分页

    @Override
    @RequestMapping(
    		params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY },
    		method = RequestMethod.GET
    		)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
    public List<Privilege> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @Override
    @RequestMapping(
    		params = { QueryConstants.PAGE, QueryConstants.SIZE },
    		method = RequestMethod.GET
    		)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
    public List<Privilege> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, null, null, uriBuilder, response);
    }

    @Override
    @RequestMapping(
    		params = { QueryConstants.SORT_BY },
    		method = RequestMethod.GET
    		)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
    public List<Privilege> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
    		@RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @RequestMapping(
    		method = RequestMethod.GET
    		)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
    public List<Privilege> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // 查找：单条记录

    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.GET
    		)
    @ResponseBody
    @Secured(Privileges.CAN_ROLE_READ)
    public Privilege findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    // 新建

    @RequestMapping(
    		method = RequestMethod.POST
    		)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Privileges.CAN_ROLE_WRITE)
    public void create(@RequestBody @Valid final Privilege resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    // 更新

    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.PUT
    		)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Privileges.CAN_ROLE_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody @Valid final Privilege resource) {
        updateInternal(id, resource);
    }

    // 删除

    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.DELETE
    		)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Privileges.CAN_ROLE_WRITE)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IPrivilegeService getService() {
        return service;
    }
	
}
