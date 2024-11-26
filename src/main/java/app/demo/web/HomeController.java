package app.demo.web;

import app.demo.config.CustomUserDetails;
import app.demo.domain.QuizUser;
import app.demo.domain.QuizUserRegistrationDto;
import app.demo.service.QuizUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final QuizUserService quizUserService;

    @Autowired
    public HomeController(QuizUserService quizUserService) {
        this.quizUserService = quizUserService;
    }

    @GetMapping("/register")
    public String register(Model model, QuizUserRegistrationDto quizUserRegistrationDto) {
        model.addAttribute("user", quizUserRegistrationDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") QuizUserRegistrationDto quizUserDto, Model model) {
        if (quizUserService.existsByUsername(quizUserDto.getUsername())) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }
        quizUserService.save(quizUserDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, QuizUserRegistrationDto quizUserRegistrationDto) {
        model.addAttribute("user", quizUserRegistrationDto);
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
   
        QuizUser user = userDetails.getQuizUser();
        Integer totalScore = user.getTotalScore(); 
        List<QuizUser> topScores = quizUserService.getTop10Users();

        model.addAttribute("user", user);
        model.addAttribute("totalScore", totalScore != null ? totalScore : 0);
        model.addAttribute("topScores", topScores);

        return "home";
    }
}
