package io.junq.examples.common.web.listeners;

import static io.junq.examples.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

import io.junq.examples.common.util.LinkUtil;
import io.junq.examples.common.web.IUriMapper;
import io.junq.examples.common.web.events.SingleResourceRetrievedEvent;
import io.junq.examples.usercenter.util.UserCenterMapping;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public SingleResourceRetrievedDiscoverabilityListener() {
    	super();
    }
    

    @Override
    public final void onApplicationEvent(final SingleResourceRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverGetAllURI(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }
    
    protected void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(UserCenterMapping.BASE + PATH_SEP + uriMapper.getUriBase(clazz)).build().encode().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }
	
}
