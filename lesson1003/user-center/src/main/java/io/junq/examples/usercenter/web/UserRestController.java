package io.junq.examples.usercenter.web;

import java.util.List;
import java.util.concurrent.Callable;

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
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import io.junq.examples.common.util.QueryConstants;
import io.junq.examples.common.web.controller.AbstractController;
import io.junq.examples.common.web.controller.ISortingController;
import io.junq.examples.usercenter.persistence.model.User;
import io.junq.examples.usercenter.service.IUserService;
import io.junq.examples.usercenter.util.UserCenter.Privileges;
import io.junq.examples.usercenter.util.UserCenterMapping;

@Controller
@RequestMapping(value = UserCenterMapping.USERS)
public class UserRestController extends AbstractController<User> implements ISortingController<User> {
	
	@Autowired
    private IUserService service;
	
    @Autowired
    private io.junq.examples.usercenter.service.AsyncService asyncService;

    public UserRestController() {
        super(User.class);
    }

    // API

    // 查找：所有、分页

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<User> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
    }

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<User> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return findPaginatedAndSortedInternal(page, size, null, null);
    }

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<User> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public List<User> findAll(final HttpServletRequest request) {
        return findAllInternal(request);
    }

    // 查找：单条记录

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public User findOne(@PathVariable("id") final Long id) {
        return findOneInternal(id);
    }
    
    @RequestMapping(params = { "name" }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.CAN_USER_READ)
    public User findOneByName(@RequestParam(value = "name") final String name) {
        return getService().findByName(name);               
    }

    // 新建

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Privileges.CAN_USER_WRITE)
    public void create(@RequestBody @Valid final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }
    
    @RequestMapping(value = "/callable", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Privileges.CAN_USER_WRITE)
    @ResponseBody
    public Callable<User> createWithCallable(@RequestBody @Valid final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
    	return new Callable<User>() {
    		@Override
    		public User call() {
				return service.createSlow(resource);
    		}
    	};
    }
   
    @RequestMapping(value = "/deferred", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Privileges.CAN_USER_WRITE)
    @ResponseBody
    public DeferredResult<User> createWithDeferredResult(@RequestBody @Valid final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
    	DeferredResult<User> result = new DeferredResult<User>();
    	asyncService.scheduleCreateUser(resource, result);
    	return result;
    }
    
    @RequestMapping(value = "/async", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUserWithAsync(@RequestBody @Valid final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) throws InterruptedException {
    	asyncService.createUserAsync(resource);
    	final String location = uriBuilder.path("/users").queryParam("name", resource.getName()).build().encode().toString();
    	response.setHeader("Location", location);
    }
    
    // 更新

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Privileges.CAN_USER_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody final User resource) {
        updateInternal(id, resource);
    }

    // 删除

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Privileges.CAN_USER_WRITE)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IUserService getService() {
        return service;
    }
    
}
