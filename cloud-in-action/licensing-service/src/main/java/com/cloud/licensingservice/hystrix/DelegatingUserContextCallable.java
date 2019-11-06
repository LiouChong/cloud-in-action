package com.cloud.licensingservice.hystrix;

import com.cloud.licensingservice.util.UserContext;
import com.cloud.licensingservice.util.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/6 16:51
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);
        try {
            return delegate.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}
