# Sample Spring Boot Project to show how to get to async code execution fast.

This Project shows how to get from a sync execution of the SomeService method to a async Mono in 2 ways.
+ creating a Mono directly
+ Using @EnableAsync and @Aysnc annotated methods

sample output when calling

+ localhost:8080/saySync?something=test
+ localhost:8080/sayAsync?something=test
+ localhost:8080/sayAsyncOther?something=test

```
2019-07-09 13:38:11.639  INFO 65711 --- [ctor-http-nio-3] d.s.async.mono.demo.SampleController     : start saySync
2019-07-09 13:38:11.642  INFO 65711 --- [ctor-http-nio-3] dev.silas.async.mono.demo.SomeService    : start say
2019-07-09 13:38:13.646  INFO 65711 --- [ctor-http-nio-3] dev.silas.async.mono.demo.SomeService    : start say
2019-07-09 13:38:19.871  INFO 65711 --- [ctor-http-nio-3] d.s.async.mono.demo.SampleController     : start sayAsync
2019-07-09 13:38:19.881  INFO 65711 --- [ctor-http-nio-3] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-07-09 13:38:19.886  INFO 65711 --- [         task-1] dev.silas.async.mono.demo.SomeService    : start sayLater
2019-07-09 13:38:19.886  INFO 65711 --- [         task-1] dev.silas.async.mono.demo.SomeService    : start say
2019-07-09 13:38:21.891 ERROR 65711 --- [         task-1] dev.silas.async.mono.demo.SomeService    : fail sayLater
2019-07-09 13:38:28.825  INFO 65711 --- [ctor-http-nio-3] d.s.async.mono.demo.SampleController     : start sayAsyncDirect
2019-07-09 13:38:28.827  INFO 65711 --- [ctor-http-nio-3] d.s.async.mono.demo.SampleController     : start saySync
2019-07-09 13:38:28.828  INFO 65711 --- [ctor-http-nio-3] dev.silas.async.mono.demo.SomeService    : start say
2019-07-09 13:38:30.830  INFO 65711 --- [ctor-http-nio-3] dev.silas.async.mono.demo.SomeService    : start say
```

so the direct call is in the same threadpool
the @Async Method spawns a threadpool
the direct Mono.create() uses the same Thread
