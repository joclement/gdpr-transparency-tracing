package berlin.tu.peng.tracingproject.highscoreservice11.api;

import berlin.tu.peng.tracingproject.highscoreservice11.service.HighscoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HighscoreController {

    private HighscoreService highscoreService;

    public HighscoreController(HighscoreService highscoreService) {
        this.highscoreService = highscoreService;
    }

    @GetMapping(value = "/game/highscores", produces = APPLICATION_JSON_VALUE)
    public List<Integer> getHighscores(){
        return highscoreService.getHighscores();
    }
}
