package dev.silas.async.mono.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    private Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    public SampleController(@Autowired ISomeService service) {
        this.service = service;
    }

    private ISomeService service;

    @GetMapping("/saySync")
    public String saySync(@RequestParam String something) throws SomeService.SomeException {
        LOGGER.info("start saySync");
        return service.say(something);
    }


    @GetMapping("/sayAsync")
    public Mono<String> sayAsync(@RequestParam String something) {
        LOGGER.info("start sayAsync" );
        return Mono.fromFuture(service.sayLater(something));
    }

    @GetMapping("/sayAsyncOther")
    public Mono<String> sayAsyncDirect(@RequestParam String something) {
        LOGGER.info("start sayAsyncDirect" );
        return Mono.create(sink -> {
            try {
                var result = saySync(something);
                sink.success(result);
            } catch (Exception e) {
                 sink.error(e);
            }
        });
    }


    @ExceptionHandler(SomeService.SomeException.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.ok("random error happend =P");
    }
}
