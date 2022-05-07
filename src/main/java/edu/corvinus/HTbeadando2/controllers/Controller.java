package edu.corvinus.HTbeadando2.controllers;
import edu.corvinus.HTbeadando2.models.LoginForm;
import edu.corvinus.HTbeadando2.models.RegistrationForm;
import edu.corvinus.HTbeadando2.models.User;
import edu.corvinus.HTbeadando2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
@org.springframework.stereotype.Controller

public class Controller {

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    MessageDigest md = MessageDigest.getInstance("SHA-512");
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final UserRepository repository;

    @Autowired
    public Controller(UserRepository repository) throws NoSuchAlgorithmException {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(RegistrationForm registrationForm) {
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginForm(LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/registration")
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult) throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors occurred!");
            return "registration";
        }

        logger.info("Registering user with username: {}", registrationForm.getUserName());
        final boolean userIsRegistered = repository.findByUserName(registrationForm.getUserName()).isPresent();
        if (!userIsRegistered) {
            repository.save(new User(registrationForm.getUserName(),get_SHA_512_SecurePassword(registrationForm.getPassword(),"salt")));
        }
        return "redirect:/welcome?userName=" + registrationForm.getUserName();
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors occurred!");
            return "login";
        }

        logger.info("Logging user with username: {}", loginForm.getUserName());
        final boolean userIsRegistered = repository.findByUserName(loginForm.getUserName()).isPresent();
        if (!userIsRegistered) {
            return "redirect:/registration";
        }

        return "redirect:/welcomelogin?userName=" + loginForm.getUserName();
    }

    @GetMapping("/welcome")
    public String showWelcomePage(@RequestParam(required = false) String userName) {
        final Optional<User> foundUser = repository.findByUserName(userName);
        if (foundUser.isEmpty()) {
            return "redirect:/";
        }
        return "welcome";
        }

    @GetMapping("/welcomelogin")
    public String showWelcomeLoginPage(@RequestParam(required = false) String userName) {
        final Optional<User> foundUser = repository.findByUserName(userName);
        if (foundUser.isEmpty()) {
            return "redirect:/";
        }
        return "welcomelogin";
    }
}
