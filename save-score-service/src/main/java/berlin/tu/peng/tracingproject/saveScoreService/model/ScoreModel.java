package berlin.tu.peng.tracingproject.saveScoreService.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "score")
public class ScoreModel {

    @Id
    private String userName;
    private Integer score;

    public String getUserName() {
        return userName;
    }

    public ScoreModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public ScoreModel setScore(Integer score) {
        this.score = score;
        return this;
    }
}
