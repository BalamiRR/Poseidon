package com.nnk.springboot.controllers;

import org.springframework.ui.Model;
import com.nnk.springboot.config.PasswordValidator;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    @GetMapping
    public String signUp(Model model){
        model.addAttribute(new User());
        return "signup";
    }

    @PostMapping
    public String signupUser(@Valid @ModelAttribute("user") User user,
                              BindingResult result, Model model, RedirectAttributes redirAttrs) {
        String signupError = null;
        String patternError = null;
        // Validate username
        User existsUser = userService.findByUsername(user.getUsername());
        if (existsUser != null) {
            signupError = "The username already exists";
            model.addAttribute("signupError", true);
        }

        // Validate password
        String userPassword = user.getPassword();
        boolean valid = PasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }

        log.info("signupError = {}", signupError);
        log.info("patternError = {}", patternError);
        log.info("hasErrors = {}", result.hasErrors());
        log.info("Password valid: {}", valid);
        log.info("Result errors: {}", result.getAllErrors());

        if (signupError == null && patternError == null && (!result.hasErrors())) {
            userService.save(user);
            redirAttrs.addFlashAttribute("message", "You've successfully signed up, please login.");
            return "redirect:/login";
        }

        return "signup";

    }

}
