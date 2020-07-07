package berlin.tu.peng.tracingproject.savescoreservice11.api;

import berlin.tu.peng.tracingproject.personaldatajaegerclient.PersonalDataSpanHelper;
import berlin.tu.peng.tracingproject.savescoreservice11.model.ScoreModel;
import berlin.tu.peng.tracingproject.savescoreservice11.service.SaveScoreService;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class saveScoreController {

    private final SaveScoreService saveScoreService;
    private final Tracer tracer;

    final static String PATH = "/game/savescore";
    final static String PATH_WITH_USERNAME = PATH + "/{username}";

    public saveScoreController(SaveScoreService saveScoreService, Tracer tracer) {
        this.saveScoreService = saveScoreService;
        this.tracer = tracer;
    }

    @PutMapping(value = PATH_WITH_USERNAME)
    public void saveUserScore(@PathVariable String username, @RequestBody ScoreModel score) {
        Span span = tracer.buildSpan("get highscores").start();
        new PersonalDataSpanHelper(span)
                .setPurpose("sevice fullfilment")
                .setDataCategory("Player Performance Data")
                .setAutomated(true) //used to determine Players for Snake World Champuionchips
                .setStorageDuration("forever")
                .setTransferredTo3rdParty(false);
        span.finish();

        final ScoreModel scoreModel = new ScoreModel().setUserName(username).setScore(score.getScore());
        saveScoreService.saveScore(scoreModel);
    }


    @GetMapping(value = PATH_WITH_USERNAME, produces = APPLICATION_JSON_VALUE)
    public ScoreModel getUserScore(@PathVariable String username){
        Span span = tracer.buildSpan("get highscores").start();
        new PersonalDataSpanHelper(span)
                .setPurpose("sevice fullfilment")
                .setDataCategory("Logging Information")
                .setStorageDuration("forever")
                .setTransferredTo3rdParty(false);
        span.finish();


        return saveScoreService.getScore(username);
    }
}
