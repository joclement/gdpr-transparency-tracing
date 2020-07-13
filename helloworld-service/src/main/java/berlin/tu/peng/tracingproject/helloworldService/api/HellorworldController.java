package berlin.tu.peng.tracingproject.helloworldService.api;

import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.*;
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
    public String sayHello() {
        Span span = tracer.buildSpan("hello world").start();
        new PersonalDataSpanHelper(span)
                .addPurpose("dummy purpose")
                .addDataCategory("dummy data category")
                .addRecipient("recipient1")
                .addRecipient("recipient2")
                .setTransferredTo3rdParty(false)
                .setAutomated(false)
                .setStorageDuration("1h").addOrigin("world").finishSpan();
        return "Hello World";
    }
}
