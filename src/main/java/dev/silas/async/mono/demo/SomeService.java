package dev.silas.async.mono.demo;

import io.netty.util.concurrent.CompleteFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class SomeService implements ISomeService {

    private final Logger LOGGER = LoggerFactory.getLogger(SomeService.class);


    public String say(String something) throws SomeException {
        LOGGER.info("start say");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new SomeException(e);
        }
        if(new Random().nextInt() % 2 == 0){
            throw new SomeException();
        }
        LOGGER.info("start say" );
        return something;
    }

    @Async
    @Override
    public CompletableFuture<String> sayLater(String something) {
        LOGGER.info("start sayLater");
        try {
            var result = say(something);
            LOGGER.info("end sayLater");
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            LOGGER.error("fail sayLater");
           return CompletableFuture.failedFuture(e);
        }
    }

    static class SomeException extends Exception {

        SomeException() {
            super();
        }

        SomeException(Exception other) {
            super(other);
        }




    }
}
