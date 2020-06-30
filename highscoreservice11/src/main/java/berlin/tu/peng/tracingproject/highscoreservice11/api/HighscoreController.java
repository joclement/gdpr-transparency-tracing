package berlin.tu.peng.tracingproject.highscoreservice11.api;

import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreservice11.service.HighscoreService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Span;
import io.opentracing.Tracer;


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
    public Iterable<HighscoreModel> getHighscores(@AuthenticationPrincipal Jwt jwt ){
        Span span = tracer.buildSpan("get highscore").start();
        span.setTag("purpose", "service fullfilment");
        span.finish();


        System.out.println(jwt.getClaims().get("user_name"));
        return highscoreService.getHighscores();
    }
}
