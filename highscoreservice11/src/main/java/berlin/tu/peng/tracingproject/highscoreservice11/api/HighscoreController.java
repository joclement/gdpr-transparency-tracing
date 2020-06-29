package berlin.tu.peng.tracingproject.highscoreservice11.api;

import berlin.tu.peng.tracingproject.highscoreservice11.service.HighscoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Span;
import io.opentracing.Tracer;

import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HighscoreController {

    private HighscoreService highscoreService;
    private Tracer tracer;

    public HighscoreController(Tracer tracer, HighscoreService highscoreService) {
        this.highscoreService = highscoreService;
        this.tracer = tracer;
    }

    @GetMapping(value = "/game/highscores", produces = APPLICATION_JSON_VALUE)
    public List<Integer> getHighscores(){
        Span span = tracer.buildSpan("get highscore").start();
        span.setTag("purpose", "service fullfilment");
        span.finish();

        return highscoreService.getHighscores();
    }
}
