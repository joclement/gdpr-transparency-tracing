package berlin.tu.peng.tracingproject.highscoreservice11.service;

import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreModel;
import berlin.tu.peng.tracingproject.highscoreservice11.model.HighscoreRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class HighscoreService {

    private final HighscoreRepository userRepository;

    public HighscoreService(HighscoreRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<HighscoreModel> getHighscores(){
        return userRepository.findAll();
    }
}
