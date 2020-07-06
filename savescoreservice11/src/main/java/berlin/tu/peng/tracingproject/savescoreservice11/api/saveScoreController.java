package berlin.tu.peng.tracingproject.savescoreservice11.api;

import berlin.tu.peng.tracingproject.savescoreservice11.model.ScoreModel;
import berlin.tu.peng.tracingproject.savescoreservice11.service.SaveScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class saveScoreController {

    private final SaveScoreService saveScoreService;

    final static String PATH = "/game/savescore";

    public saveScoreController(SaveScoreService saveScoreService) {
        this.saveScoreService = saveScoreService;
    }

    @PutMapping(value = PATH, consumes = APPLICATION_JSON_VALUE)
    public void saveUserScore(@AuthenticationPrincipal Jwt jwt, @RequestBody ScoreModel scoreModel) {
        scoreModel.setUserName(jwt.getClaims().get("user_name").toString());
        saveScoreService.saveScore(scoreModel);
    }

    @GetMapping(value = PATH, produces = APPLICATION_JSON_VALUE)
    public ScoreModel getUserScore(@AuthenticationPrincipal Jwt jwt){
        return saveScoreService.getScore(jwt.getClaims().get("user_name").toString());
    }
}
