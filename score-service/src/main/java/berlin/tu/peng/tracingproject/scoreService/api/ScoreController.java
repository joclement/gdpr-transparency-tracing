package berlin.tu.peng.tracingproject.scoreService.api;

import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.*;
import berlin.tu.peng.tracingproject.scoreService.model.ScoreModel;
import berlin.tu.peng.tracingproject.scoreService.service.SaveScoreService;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class ScoreController {

    private final SaveScoreService saveScoreService;
    private final Tracer tracer;

    final static String PATH = "/game/savescore";
    final static String PATH_WITH_USERNAME = PATH + "/{username}";

    public ScoreController(SaveScoreService saveScoreService, Tracer tracer) {
        this.saveScoreService = saveScoreService;
        this.tracer = tracer;
    }

    @PutMapping(value = PATH_WITH_USERNAME)
    public void saveUserScore(@PathVariable String username, @RequestBody ScoreModel score) {
        Span span = tracer.buildSpan("save score").start();
        new PersonalDataSpanHelper(span)
                .addPurpose(Purpose.SERVICE_FULLFILMENT)
                .addDataCategory(Category.PLAYER_PERFORMANCE)
                .setAutomated(true) //used to determine Players for Snake World Champuionchips
                .setStorageDuration(StorageDuration.END_OF_UNIVERSE)
                .setTransferredTo3rdParty(false)
                .newGroup()
                .addPurpose(Purpose.COMMERCIAL)
                .addDataCategory(Category.GENDER)
                .setAutomated(false)
                .setStorageDuration(StorageDuration.ONE_MONTH)
                .setTransferredTo3rdParty(true)
                .addRecipient(Recipient.DOOFENSCHMIRTZ_EVIL_INC)
                .finishSpan();

        final ScoreModel scoreModel = new ScoreModel().setUserName(username).setScore(score.getScore());
        saveScoreService.saveScore(scoreModel);
    }


    @GetMapping(value = PATH_WITH_USERNAME, produces = APPLICATION_JSON_VALUE)
    public ScoreModel getUserScore(@PathVariable String username){
        Span span = tracer.buildSpan("get score").start();
        new PersonalDataSpanHelper(span)
                .addPurpose(Purpose.ADMINISTRATION)
                .addDataCategory(Category.IP)
                .setStorageDuration(StorageDuration.ONE_YEAR)
                .setTransferredTo3rdParty(false)
                .finishSpan();

        return saveScoreService.getScore(username);
    }
}
