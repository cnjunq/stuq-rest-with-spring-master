package io.junq.examples.common.web.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import io.junq.examples.common.web.exception.IJConflictException;
import io.junq.examples.common.web.exception.ValidationException;

public final class RestUtil {

	private RestUtil() {
        throw new AssertionError();
    }

    //

    /**
     * 当向另一个服务发起“创建”请求时，如果返回响应状态码不是201的话，将抛出异常
     * 
     * @param createResponse
     *            “创建”请求的返回响应
     * @param message
     * 			  创建失败时的消息
     */
    public static void propagateStatusCodeOnCreate(final ResponseEntity<?> createResponse, final String message) {
        if (createResponse.getStatusCode().value() == 409) {
            throw new IJConflictException(message);
        }
        if (createResponse.getStatusCode().value() != 201) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * 用来处理异常发生的情况
     * 
     * @param ex
     *            非http状态码异常
     * @param message
     * 			  失败时的消息
     */
    public static void propagateStatusCodeOnException(final HttpStatusCodeException ex, final String message) {
        if (ex.getStatusCode().value() == 409) {
            throw new ValidationException(ex.getStatusText());
        }

        throw new IllegalStateException(message);
    }
    
}
