package berlin.tu.peng.tracingproject.helloworldservice11.api;

import berlin.tu.peng.tracingproject.personaldatajaegerclient.*;
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
                .addPurpose(Purpose.DEVELOPMENT)
                .addDataCategory(Category.BIRTHDAY)
                .addRecipient(Recipient.DOOFENSCHMIRTZ_EVIL_INC)
                .addRecipient(Recipient.OWCA)
                .setTransferredTo3rdParty(false)
                .setAutomated(false)
                .setStorageDuration(StorageDuration.NO_RETENTION)
                .addOrigin(Origin.PERRY_THE_PLATYPUS)
                .finishSpan();
        return "Hello World";
    }
}
