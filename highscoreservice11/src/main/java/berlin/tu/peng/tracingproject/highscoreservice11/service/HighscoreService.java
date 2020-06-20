package berlin.tu.peng.tracingproject.highscoreservice11.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HighscoreService {

    public List<Integer> getHighscores(){
        return Collections.singletonList(42);
    }
}
