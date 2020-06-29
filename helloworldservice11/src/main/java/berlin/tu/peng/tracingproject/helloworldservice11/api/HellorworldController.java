package berlin.tu.peng.tracingproject.helloworldservice11.api;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellorworldController {

    private Tracer tracer;

    public HellorworldController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping(path = "/helloworld")
    public String sayHello(){
        Span span = tracer.buildSpan("hello world").start();
        span.setTag("purpose", "fun");
        span.finish();
        return "Hello World";
    }
}
