package io.junq.examples.common.web.listeners;

import static io.junq.examples.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

import io.junq.examples.common.web.IUriMapper;
import io.junq.examples.common.web.events.AfterResourceCreatedEvent;
import io.junq.examples.usercenter.util.UserCenterMapping;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ResourceCreatedDiscoverabilityListener implements ApplicationListener<AfterResourceCreatedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public ResourceCreatedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final AfterResourceCreatedEvent ev) {
        Preconditions.checkNotNull(ev);

        final String idOfNewResource = ev.getIdOfNewResource();
        addLinkHeaderOnEntityCreation(ev.getUriBuilder(), ev.getResponse(), idOfNewResource, ev.getClazz());
    }

    protected void addLinkHeaderOnEntityCreation(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final String idOfNewEntity, final Class clazz) {
        final String path = calculatePathToResource(clazz);
        final String locationValue = uriBuilder.path(path).build().expand(idOfNewEntity).encode().toUriString();

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    protected String calculatePathToResource(final Class clazz) {
        final String resourceName = uriMapper.getUriBase(clazz);
        final String path = UserCenterMapping.BASE + PATH_SEP + resourceName + "/{id}";
        return path;
    }

}
