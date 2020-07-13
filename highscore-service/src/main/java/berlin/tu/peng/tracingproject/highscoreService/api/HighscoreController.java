package berlin.tu.peng.tracingproject.highscoreService.api;

import berlin.tu.peng.tracingproject.highscoreService.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreService.service.HighscoreService;
import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.Category;
import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.PersonalDataSpanHelper;
import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.Purpose;
import berlin.tu.peng.tracingproject.transparencyOpentracingHelper.StorageDuration;
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
    public HighscoreModel getTopHighscore() {
        Span span = tracer.buildSpan("get highscores").start();
        new PersonalDataSpanHelper(span)
                .addPurpose(Purpose.SERVICE_FULLFILMENT)
                .addDataCategory(Category.IP)
                .setStorageDuration(StorageDuration.END_OF_UNIVERSE)
                .setTransferredTo3rdParty(false)
                .finishSpan();

        return highscoreService.getTopHighscore();
    }

    @GetMapping(value = "/game/highscores", produces = APPLICATION_JSON_VALUE)
    public List<HighscoreModel> getHighscores() {
        Span span = tracer.buildSpan("get highscores").start();
        new PersonalDataSpanHelper(span)
                .addPurpose(Purpose.SERVICE_FULLFILMENT)
                .addDataCategory(Category.IP)
                .setStorageDuration(StorageDuration.ONE_DECADE)
                .setTransferredTo3rdParty(false)
                .finishSpan();

        final List<HighscoreModel> result = highscoreService.getHighscores();
        result.forEach(item -> System.out.println(item.getId()));

        return result;
    }

    @PostMapping(value = "/game/highscores", consumes = APPLICATION_JSON_VALUE)
    public void addHighscore(@RequestBody final HighscoreModel highscoreModel) {
        Span span = tracer.buildSpan("get highscores").start();
        new PersonalDataSpanHelper(span)
                .addPurpose(Purpose.SERVICE_FULLFILMENT)
                .addDataCategory(Category.PLAYER_PERFORMANCE)
                .setAutomated(true) //used to determine Players for Snake World Champuionchips
                .setStorageDuration(StorageDuration.END_OF_UNIVERSE)
                .setTransferredTo3rdParty(false)
                .finishSpan();

        highscoreService.addHigshcore(highscoreModel);
    }

    //todo span.finsih??
    private PersonalDataSpanHelper getPersonalDataSpanHelper(String spanName) {
        Span span = tracer.buildSpan(spanName).start();
        return new PersonalDataSpanHelper(span);
    }
}
