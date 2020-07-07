package berlin.tu.peng.tracingproject.highscoreservice11.api;

import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreservice11.service.HighscoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Span;
import io.opentracing.Tracer;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HighscoreController {

    private final HighscoreService highscoreService;
    private final Tracer tracer;

    public HighscoreController(Tracer tracer, HighscoreService highscoreService) {
        this.highscoreService = highscoreService;
        this.tracer = tracer;
    }

    @GetMapping(value = "/game/highscores/top", produces = APPLICATION_JSON_VALUE)
    public HighscoreModel getTopHighscore(){
        return highscoreService.getTopHighscore();
    }

    @GetMapping(value = "/game/highscores", produces = APPLICATION_JSON_VALUE)
    public List<HighscoreModel> getHighscores(){
        Span span = tracer.buildSpan("get highscore").start();
        span.setTag("purpose", "service fullfilment");
        span.finish();

        final List<HighscoreModel> result = highscoreService.getHighscores();
        result.forEach(item -> System.out.println(item.getId()));

        return result;
    }

    @PostMapping(value = "/game/highscores", consumes = APPLICATION_JSON_VALUE)
    public void addHighscore(@RequestBody final HighscoreModel highscoreModel){
        highscoreService.addHigshcore(highscoreModel);
    }
}
