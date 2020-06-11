package berlin.tu.peng.tracingproject.helloworldservice11.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellorworldController {

    @GetMapping(path = "/helloworld")
    public String sayHello(){
        return "Hello World";
    }
}
