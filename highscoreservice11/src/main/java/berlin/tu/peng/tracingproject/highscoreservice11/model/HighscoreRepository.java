package berlin.tu.peng.tracingproject.highscoreservice11.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighscoreRepository extends JpaRepository<HighscoreModel, String> {
            HighscoreModel findFirstByOrderByHighscoreDesc();
}
