package app.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "QUIZUSER") 
public class QuizUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore = 0; 

    public QuizUser() {}

    public QuizUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalScore = 0; 
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.totalScore += points;
        }
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
