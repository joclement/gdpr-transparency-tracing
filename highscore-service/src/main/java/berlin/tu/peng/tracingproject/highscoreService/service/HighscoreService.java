package berlin.tu.peng.tracingproject.highscoreService.service;

import berlin.tu.peng.tracingproject.highscoreService.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreService.model.HighscoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
public class HighscoreService {

    private final HighscoreRepository highscoreRepository;

    public HighscoreService(HighscoreRepository highscoreRepository) {
        this.highscoreRepository = highscoreRepository;
    }

    @Transactional(readOnly = true)
    public List<HighscoreModel> getHighscores() {
        return highscoreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public HighscoreModel getTopHighscore() {
        return highscoreRepository.findFirstByOrderByHighscoreDesc();
    }

    @Transactional
    public void addHigshcore(final HighscoreModel highscoreModel){
        highscoreModel.setId(UUID.randomUUID());
        highscoreRepository.save(highscoreModel); //todo only save in special occasions?
    }
}
