package io.junq.examples.common.persistence;

import io.junq.examples.common.persistence.exception.IJEntityNotFoundException;
import io.junq.examples.common.web.exception.IJBadRequestException;

public class ServicePreconditions {
	
    private ServicePreconditions() {
        throw new AssertionError();
    }

    /**
     * 确保作为参数传入的实体不为null
     * 
     * @param entity
     *            一个对象引用
     * @return 验证无误后返回不为null的对象引用
     * @throws IJEntityNotFoundException
     *             如果 {@code entity} 为null时
     */
    public static <T> T checkEntityExists(final T entity) {
        if (entity == null) {
            throw new IJEntityNotFoundException();
        }
        return entity;
    }

    public static void checkEntityExists(final boolean entityExists) {
        if (!entityExists) {
            throw new IJEntityNotFoundException();
        }
    }

    public static void checkOKArgument(final boolean okArgument) {
        if (!okArgument) {
            throw new IJBadRequestException();
        }
    }
}
