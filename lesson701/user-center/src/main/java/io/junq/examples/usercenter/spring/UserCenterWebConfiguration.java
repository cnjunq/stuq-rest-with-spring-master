package io.junq.examples.usercenter.spring;

import java.util.Iterator;
import java.util.List;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@ComponentScan({ "io.junq.examples.usercenter.web", "io.junq.examples.common.web" })
@EnableWebMvc
public class UserCenterWebConfiguration extends WebMvcConfigurerAdapter {

	public UserCenterWebConfiguration() {
		super();
	}
	
	public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		final Iterator<HttpMessageConverter<?>> converterIterator = converters.stream()
					.filter(c -> c instanceof AbstractJackson2HttpMessageConverter).iterator();
		while (converterIterator.hasNext()) {
			final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) converterIterator.next();
			converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		}
	}
	
	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		// 方法一：URL后缀
//		configurer
//			.favorPathExtension(true)
//			.favorParameter(false)
//			.ignoreAcceptHeader(true)
//			.defaultContentType(MediaType.APPLICATION_JSON);
		
		// 方法二：查询参数
//		configurer
//			.favorPathExtension(false)
//			.favorParameter(true)
//			.parameterName(QueryConstants.FORMAT)
//			.mediaType(QueryConstants.VALUE_JSON, MediaType.APPLICATION_JSON)
//			.mediaType(QueryConstants.VALUE_XML, MediaType.APPLICATION_XML)
//			.ignoreAcceptHeader(true)
//			.defaultContentType(MediaType.APPLICATION_JSON);
		
		// 方法三：Header Accept字段
		configurer
			.favorPathExtension(false)
			.favorParameter(false)
			.ignoreAcceptHeader(false)
			.defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Bean
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
}
