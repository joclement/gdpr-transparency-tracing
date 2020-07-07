package berlin.tu.peng.tracingproject.helloworldservice11.api;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import berlin.tu.peng.tracingproject.personaldatajaegerclient.PersonalDataSpanHelper;

import java.util.List;


@RestController
public class HellorworldController {

    private Tracer tracer;

    public HellorworldController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping(path = "/helloworld")
    public String sayHello(){
        Span span = tracer.buildSpan("hello world").start();
        PersonalDataSpanHelper spanHelper = new PersonalDataSpanHelper(span);
        spanHelper.setPurpose("dummy purpose")
                  .setDataCategory("dummy data category");
        spanHelper.setRecipients(List.of("recipient1", "recipient2"));
        spanHelper.setTransferredTo3rdParty(false);
        spanHelper.setAutomated(false);
        spanHelper.setStorageDuration("1h").setOrigin("world");
        span.finish();
        return "Hello World";
    }
}
