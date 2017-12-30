package io.junq.examples.usercenter.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import io.junq.examples.usercenter.persistence.model.User;

@Service
public class AsyncService {
	@Autowired
    private IUserService userService;

    public static final long DELAY = 10000L;

    private final ConcurrentMap<String, Pair<User, DeferredResult<User>>> deferredResultMap = new ConcurrentHashMap<String, Pair<User, DeferredResult<User>>>();

    @Async
    public Future<User> createUserAsync(User resource) throws InterruptedException {
        resource.setStatus("In Progress");

        final User result = userService.create(resource);
        Thread.sleep(AsyncService.DELAY);

        result.setStatus("Ready");

        userService.update(result);
        return new AsyncResult<User>(result);
    }
    
    public void scheduleCreateUserWithAsyncResultSetting(User resource, DeferredResult<User> result) {
        final String key = resource.getName();
        ImmutablePair pair = new ImmutablePair<User, DeferredResult<User>>(resource, result);
        deferredResultMap.put(key, pair);
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                deferredResultMap.remove(key);
            }
        });
    }

    public void scheduleCreateUser(User resource, DeferredResult<User> deferredResult) {
        CompletableFuture.supplyAsync(() -> userService.createSlow(resource)).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
    }

    @Scheduled(fixedRate = DELAY)
    public void processUserDtoQueue() {
        deferredResultMap.forEach((k, pair) -> {
            final User created = userService.create(pair.getLeft());
            pair.getRight().setResult(created);
        });
    }
}
