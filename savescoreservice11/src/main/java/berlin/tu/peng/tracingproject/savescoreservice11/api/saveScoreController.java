package berlin.tu.peng.tracingproject.savescoreservice11.api;

import berlin.tu.peng.tracingproject.savescoreservice11.model.ScoreModel;
import berlin.tu.peng.tracingproject.savescoreservice11.service.SaveScoreService;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class saveScoreController {

    private final SaveScoreService saveScoreService;

    final static String PATH = "/game/savescore";
    final static String PATH_WITH_USERNAME = PATH + "/{username}";

    public saveScoreController(SaveScoreService saveScoreService) {
        this.saveScoreService = saveScoreService;
    }

    @PutMapping(value = PATH_WITH_USERNAME)
    public void saveUserScore(@PathVariable String username, @RequestBody ScoreModel score) {
        final ScoreModel scoreModel = new ScoreModel().setUserName(username).setScore(score.getScore());

        saveScoreService.saveScore(scoreModel);
    }


    @GetMapping(value = PATH_WITH_USERNAME, produces = APPLICATION_JSON_VALUE)
    public ScoreModel getUserScore(@PathVariable String username){
        return saveScoreService.getScore(username);
    }
}
