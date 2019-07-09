package dev.silas.async.mono.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class SomeService {

    public String say(String something) throws InterruptedException, SomeException {
        Thread.sleep(2000);
        if(new Random().nextInt() % 2 == 0){
            throw new SomeException();
        }
        return something;
    }

    @Async
    protected CompletableFuture<String> sayLater(String something) {
        try {
            var result = say(something);
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
           return CompletableFuture.failedFuture(e);
        }
    }

    public Mono<String> sayMono(String something) {
        return Mono.fromFuture(sayLater(something));
    }

    protected static class SomeException extends Exception {

    }
}
