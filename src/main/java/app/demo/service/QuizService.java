package app.demo.service;

import app.demo.domain.ApiResponse;
import app.demo.domain.Question;
import app.demo.domain.QuizUser;
import app.demo.domain.QuizUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final QuizUserRepository quizUserRepository;

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    private int questionsAnswered = 0; // Counter for answered questions
    private static final int MAX_QUESTIONS = 10;

    public QuizService(RestTemplate restTemplate,
            ObjectMapper objectMapper,
            QuizUserRepository quizUserRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.quizUserRepository = quizUserRepository;
    }

    public Question getNextQuestion(String difficulty) {
        if (questionsAnswered >= MAX_QUESTIONS) {
            logger.info("Max questions reached. Returning null to indicate end of quiz.");
            return null; // Return null if max questions have been answered
        }

        logger.info("Fetching next question for difficulty: {}", difficulty);
        if (questions.isEmpty()) {
            fetchQuestions(difficulty);
        }

        Question question = fetchRandomQuestion();
        if (question == null) {
            logger.warn("No questions available after attempting fetch.");
            throw new RuntimeException("No questions available for the specified difficulty.");
        }

        questionsAnswered++; // Increment the counter after fetching a question
        logger.info("Returning question: {}", question.getQuestion());
        return question;
    }

    private void fetchQuestions(String difficulty) {
        String url = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api.php")
                .queryParam("amount", 100)
                .queryParam("difficulty", difficulty)
                .queryParam("type", "boolean")
                .toUriString();

        logger.info("Fetching questions from API: {}", url);

        try {
            String response = restTemplate.getForObject(url, String.class);
            logger.debug("API Response: {}", response);

            ApiResponse<Question> apiResponse = objectMapper.readValue(
                    response,
                    objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Question.class));

            if (apiResponse != null && apiResponse.getResults() != null && !apiResponse.getResults().isEmpty()) {
                questions.addAll(apiResponse.getResults());
                logger.info("Fetched {} questions", apiResponse.getResults().size());
            } else {
                logger.warn("No questions found for difficulty: {}", difficulty);
            }
        } catch (Exception e) {
            logger.error("Error fetching questions from API", e);
        }
    }

    private Question fetchRandomQuestion() {
        if (questions.isEmpty()) {
            logger.warn("No more questions available to fetch.");
            return null;
        }
        return questions.remove(random.nextInt(questions.size()));
    }

    public void saveScore(QuizUser quizUser, int points) {
        // Increment user's total score
        quizUser.setTotalScore(quizUser.getTotalScore() + points);
        updateUser(quizUser); // Persist the updated user object

        logger.info("Added {} points for user '{}'. New total score: {}", points, quizUser.getUsername(),
                quizUser.getTotalScore());
    }

    public void updateUser(QuizUser quizUser) {
        quizUserRepository.save(quizUser); // Persist user to the database
        logger.debug("User '{}' updated successfully in the database.", quizUser.getUsername());
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void resetQuiz() {
        questions.clear();
        questionsAnswered = 0;
        logger.info("Quiz state reset. Ready for a new session.");
    }
}
