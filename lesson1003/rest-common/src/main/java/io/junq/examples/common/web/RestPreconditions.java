package io.junq.examples.common.web;

import org.springframework.http.HttpStatus;

import io.junq.examples.common.web.exception.IJBadRequestException;
import io.junq.examples.common.web.exception.IJConflictException;
import io.junq.examples.common.web.exception.IJResourceNotFoundException;

/**
 * 用作参数、状态判定的静态方法
 * 当判定失败时，会抛出 {@link HttpStatus} 状态码
 * 
 */
public final class RestPreconditions {

    private RestPreconditions() {
        throw new AssertionError();
    }

    // API

    /**
     * 确保作为参数传入的对象引用不为null
     *
     * @param reference
     *            对象引用
     *
     * @return 验证无误后返回不为null的对象引用
     *
     * @throws IJResourceNotFoundException
     *             如果 {@code reference} 为null时
     */
    public static <T> T checkNotNull(final T reference) {
        return checkNotNull(reference, null);
    }

    /**
     * 确保作为参数传入的对象引用不为null
     *
     * @param reference
     *            对象引用
     * @param message
     *            如果验证失败时，异常的消息
     *
     * @return 验证无误后返回不为null的对象引用
     *
     * @throws IJResourceNotFoundException
     *             如果 {@code reference} 为null时
     */
    public static <T> T checkNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new IJResourceNotFoundException(message);
        }
        return reference;
    }

    /**
     * 确保作为参数传入的对象引用不为null
     *
     * @param reference
     *            对象引用
     * @return 验证无误后返回不为null的对象引用
     *
     * @throws IJBadRequestException
     *             如果 {@code reference} 为null时
     */
    public static <T> T checkRequestElementNotNull(final T reference) {
        return checkRequestElementNotNull(reference, null);
    }

    /**
     * 确保作为参数传入的对象引用不为null
     *
     * @param reference
     *            对象引用
     * @param message
     *            如果验证失败时，异常的消息
     *
     * @return 验证无误后返回不为null的对象引用
     *
     * @throws IJBadRequestException
     *             if {@code reference} is null
     */
    public static <T> T checkRequestElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new IJBadRequestException(message);
        }
        return reference;
    }

    /**
     * 确保表达式为true
     *
     * @param expression
     *            表达式
     *
     * @throws IJConflictException
     *             如果 {@code expression} 为false
     */
    public static void checkRequestState(final boolean expression) {
        checkRequestState(expression, null);
    }

    /**
     * 确保表达式为true
     *
     * @param expression
     *            表达式
     * @param message
     *            当验证失败时，异常消息
     *
     * @throws IJConflictException
     *             如果 {@code expression} 为false
     */
    public static void checkRequestState(final boolean expression, final String message) {
        if (!expression) {
            throw new IJConflictException(message);
        }
    }
    
    /**
     * 检查资源是否找到
     */
    public static <T> T checkFound(final T resource) {
        return checkFound(resource, null);
    }

    /**
     * 检查资源是否找到
     */
    public static <T> T checkFound(final T resource, final String message) {
        if (resource == null) {
            throw new IJResourceNotFoundException(message);
        }

        return resource;
    }

}
