package berlin.tu.peng.tracingproject.savescoreservice11.service;

import berlin.tu.peng.tracingproject.savescoreservice11.model.ScoreModel;
import berlin.tu.peng.tracingproject.savescoreservice11.model.ScoreRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveScoreService {

    private final ScoreRespository scoreRespository;

    public SaveScoreService(ScoreRespository scoreRespository) {
        this.scoreRespository = scoreRespository;
    }

    @Transactional(readOnly = false)
    public void saveScore(final ScoreModel scoreModel){
        scoreRespository.save(scoreModel);
    }

    @Transactional(readOnly = true)
    public ScoreModel getScore(final String username){
        return scoreRespository.findFirstByUserName(username);
    }
}
