package com.nnk.springboot.controllers;

import com.nnk.springboot.config.PasswordValidator;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Displays the list of all users.
     * @param model model used to pass data to the view
     * @return the name of the view "user/list"
     */
    @RequestMapping("/user/list")
    public String home(Authentication authentication, Model model) {
        String name = authentication.getName();
        model.addAttribute("name", "Logged in as: " + name);
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Displays the form to create a new user.
     * @param user empty user object for the form
     * @return the view "user/add"
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * @param user the user object populated from the form
     * @param result holds validation errors
     * @param model  model used to pass data to the view
     * @return "redirect:/user/list" if successful, otherwise "user/add"
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        String duplicateError = null;
        String patternError = null;

        User existsUser = userService.findByUsername(user.getUsername());
        if (existsUser != null) {
            duplicateError = "The username already exists";
            model.addAttribute("duplicateError", true);
            result.rejectValue("username", "error.user", duplicateError);
        }

        String userPassword = user.getPassword();
        boolean valid = PasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }

        if (duplicateError == null && patternError == null && (!result.hasErrors())) {
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * @param id    the ID of the user to update
     * @param model model used to pass user data to the view
     * @return the view name "user/update"
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * @param id ID of the user to update
     * @param user updated user object
     * @param result validation binding result
     * @param model model used to pass feedback to the view
     * @return redirect to "user/list" if success, otherwise stays on "user/update"
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        String duplicateError = null;
        String patternError = null;

        User userToUpdate = userService.findById(id);
        User existsUser = userService.findByUsername(user.getUsername());
        if (!userToUpdate.getUsername().equals(user.getUsername())) {
            if (existsUser != null) {
                duplicateError = "The username already exists";
                model.addAttribute("duplicateError", true);
            }
        }
        // Validate password
        String userPassword = user.getPassword();
        boolean valid = PasswordValidator.isValid(userPassword);
        if (!valid) {
            patternError = "Password doesn't match the pattern";
            model.addAttribute("patternError", true);
        }
        if (duplicateError == null && patternError == null && (!result.hasErrors())) {
            Boolean updated = userService.updateUser(id, user);
            if (updated) {
                model.addAttribute("users", userService.findAll());
                return "redirect:/user/list";
            }
        }
        return "user/update";
    }

    /**
     * Deletes a user from the system.
     *
     * @param id    ID of the user to delete
     * @param model model used to refresh the list after deletion
     * @return redirect to "user/list" after successful deletion
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        userService.delete(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
