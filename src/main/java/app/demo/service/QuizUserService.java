package app.demo.service;

import app.demo.domain.QuizUserRegistrationDto;
import app.demo.domain.QuizUser;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface QuizUserService {
    boolean existsByUsername(String username);
    void save(QuizUserRegistrationDto quizUserDto);

     @Query("SELECT u FROM QuizUser u WHERE u.totalScore > 0 ORDER BY u.totalScore DESC")
     List<QuizUser> getTop10Users(); 
}
