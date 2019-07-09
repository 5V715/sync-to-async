package dev.silas.async.mono.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    public SampleController(@Autowired SomeService service) {
        this.service = service;
    }

    private SomeService service;

    @GetMapping("/saySync")
    public String saySync(@RequestParam String something) throws SomeService.SomeException, InterruptedException {
        return service.say(something);
    }


    @GetMapping("/sayAsync")
    public Mono<String> sayAsync(@RequestParam String something) {
        return service.sayMono(something);
    }


    @ExceptionHandler(SomeService.SomeException.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.ok("random error happend =P");
    }
}
