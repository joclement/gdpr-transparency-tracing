package berlin.tu.peng.tracingproject.highscoreservice11.service;

import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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
}
