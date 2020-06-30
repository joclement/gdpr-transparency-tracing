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
}
