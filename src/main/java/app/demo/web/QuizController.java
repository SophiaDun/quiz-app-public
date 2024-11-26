package app.demo.web;

import app.demo.domain.Question;
import app.demo.domain.QuizUser;
import app.demo.service.QuizService;
import app.demo.domain.AnswerDTO;
import app.demo.config.CustomUserDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public String getQuizPage(@RequestParam(required = false, defaultValue = "easy") String difficulty, Model model) {
        model.addAttribute("difficulty", difficulty);
        quizService.resetQuiz(); // Reset the quiz state for a new session
        return "quiz";
    }

    @GetMapping("/next")
    public ResponseEntity<Question> getNextQuestion(@RequestParam String difficulty) {
        Question question = quizService.getNextQuestion(difficulty);
        if (question == null) {
            return ResponseEntity.noContent().build(); // Return no content if no more questions are available
        }
        return ResponseEntity.ok(question);
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> submitAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody AnswerDTO answer) {
        System.out.println("Received answer: " + answer);
        QuizUser user = userDetails.getQuizUser(); // Access the QuizUser from CustomUserDetails
        boolean isCorrect = answer.getSelectedAnswer().equals(answer.getCorrectAnswer()); // Validate the answer
        if (isCorrect) {
            user.setTotalScore(user.getTotalScore() + 1); // Increment the total score if the answer is correct
            quizService.updateUser(user); // Persist the updated total score
        }
        return ResponseEntity.ok(isCorrect); 
    }

    @PostMapping("/submit")
    public ResponseEntity<Integer> submitQuiz(@AuthenticationPrincipal CustomUserDetails userDetails) {
        QuizUser user = userDetails.getQuizUser(); // Access the QuizUser from CustomUserDetails
        int score = quizService.getQuestionsAnswered(); // Get the number of questions answered
        user.setTotalScore(user.getTotalScore() + score); // Increment the user's total score
        quizService.updateUser(user); // Persist the updated user
        quizService.resetQuiz(); // Reset the quiz state
        return ResponseEntity.ok(score);
    }
}
