package app.demo.domain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;





public interface QuizUserRepository extends CrudRepository<QuizUser, Long> {
    QuizUser findByUsername(String username);
    boolean existsByUsername(String username); 
  // exclude users with score 0
  List<QuizUser> findTop10ByTotalScoreGreaterThanOrderByTotalScoreDesc(int score);
}


