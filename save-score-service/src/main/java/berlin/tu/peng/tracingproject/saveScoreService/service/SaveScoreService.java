package berlin.tu.peng.tracingproject.saveScoreService.service;

import berlin.tu.peng.tracingproject.saveScoreService.model.HighscoreModel;
import berlin.tu.peng.tracingproject.saveScoreService.model.ScoreModel;
import berlin.tu.peng.tracingproject.saveScoreService.model.ScoreRespository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class SaveScoreService {

    private final ScoreRespository scoreRespository;
    private final RestTemplate restTemplate;


    public SaveScoreService(ScoreRespository scoreRespository, RestTemplateBuilder restTemplateBuilder) {
        this.scoreRespository = scoreRespository;
        this.restTemplate = restTemplateBuilder.build();
    }


    @Transactional
    public void saveScore(final ScoreModel scoreModel){
        scoreRespository.save(scoreModel);
        postHighscore(scoreModel.getScore());
    }

    @Transactional(readOnly = true)
    public ScoreModel getScore(final String username){
        return scoreRespository.findFirstByUserName(username);
    }


    private Post postHighscore(final Integer newHighscore){
        final String url = "http://highscores:8082/game/highscores";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HighscoreModel highscoreModel = new HighscoreModel().setHighscore(newHighscore);

        final HttpEntity<HighscoreModel> entity = new HttpEntity<>(highscoreModel, headers);

        final ResponseEntity<Post> responseEntity = this.restTemplate.postForEntity(url, entity, Post.class);

        if(responseEntity.getStatusCode() == HttpStatus.CREATED || responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        } else {
            System.out.println("ERROR SENDING POST FROM savescoreservice TO highscoreservice");
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
            return null;
        }
    }
}

