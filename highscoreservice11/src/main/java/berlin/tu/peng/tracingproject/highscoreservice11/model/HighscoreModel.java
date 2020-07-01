package berlin.tu.peng.tracingproject.highscoreservice11.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "highscore")
public class HighscoreModel {

    @Id
    private UUID id;
    private Integer highscore;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getHighscore() {
        return highscore;
    }

    public void setHighscore(Integer highscore) {
        this.highscore = highscore;
    }
}
