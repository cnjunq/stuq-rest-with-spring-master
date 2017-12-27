package io.junq.examples.usercenter.web.limit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * @return 每秒限制为多少个请求
     */
    int value();

    /**
     * @return 限制条件的id(可选)
     */
    String key() default "";
	
}
