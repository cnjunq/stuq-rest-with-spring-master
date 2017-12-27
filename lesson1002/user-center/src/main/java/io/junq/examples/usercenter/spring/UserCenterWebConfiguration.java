package io.junq.examples.usercenter.spring;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.junq.examples.usercenter.web.KryoHttpMessageConverter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan({ "io.junq.examples.usercenter.web", "io.junq.examples.common.web", "io.junq.examples.common.metric.web" })
@EnableWebMvc
@EnableSwagger2
public class UserCenterWebConfiguration extends WebMvcConfigurerAdapter {

	public UserCenterWebConfiguration() {
		super();
	}
	
	public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(new KryoHttpMessageConverter());
		
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
	
	@Bean
    public Docket mainConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()) // API信息
				.select()  // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage("io.junq.examples.usercenter.web")) // 仅对web controller进行监控
				.paths(PathSelectors.any()) // 对所有路径进行监控
				.build()
				.pathMapping("/api") // api基本路径
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				;
	}
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API for user-center")
                .description("REST API documentations for user-center project.")
                .version("1.0")
                .build();
    }
    
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
