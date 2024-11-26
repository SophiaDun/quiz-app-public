package app.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.demo.domain.QuizUser;
import app.demo.domain.QuizUserRegistrationDto;
import app.demo.domain.QuizUserRepository;
import app.demo.service.QuizUserService;
import java.util.List;

@Service
public class UserServiceImpl implements QuizUserService {

    private final QuizUserRepository quizUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(QuizUserRepository quizUserRepository, PasswordEncoder passwordEncoder) {
        this.quizUserRepository = quizUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsByUsername(String username) {
        return quizUserRepository.existsByUsername(username);
    }

    @Override
    public void save(QuizUserRegistrationDto quizUserDto) {
        QuizUser user = new QuizUser();
        user.setUsername(quizUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(quizUserDto.getPassword()));
        quizUserRepository.save(user);
    }

    // getTop10Users() method to fetch the top 10 users by total score
    @Override
    public List<QuizUser> getTop10Users() {
        return quizUserRepository.findTop10ByTotalScoreGreaterThanOrderByTotalScoreDesc(0);
    }
}
