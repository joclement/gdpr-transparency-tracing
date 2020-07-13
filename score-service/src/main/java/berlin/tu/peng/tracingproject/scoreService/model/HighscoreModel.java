package berlin.tu.peng.tracingproject.scoreService.model;

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

    public HighscoreModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public Integer getHighscore() {
        return highscore;
    }

    public HighscoreModel setHighscore(Integer highscore) {
        this.highscore = highscore;
        return this;
    }
}
