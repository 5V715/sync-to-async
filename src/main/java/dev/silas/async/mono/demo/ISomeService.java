package dev.silas.async.mono.demo;

import java.util.concurrent.CompletableFuture;

public interface ISomeService {

    String say(String something) throws SomeService.SomeException;

    CompletableFuture<String> sayLater(String something);

}
