package com.calculator.cal.controller;

import com.calculator.cal.dto.UserForm;
import com.calculator.cal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        UserForm registrationForm = new UserForm();

        model.addAttribute("registrationForm", registrationForm);
        return "user-register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationForm") UserForm userForm,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {

            model.addAttribute("registrationForm", userForm);
            return "user-register";
        }

        userService.addUser(userForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user-login";
    }

    @GetMapping("/login?error")
    public String getFail(Model model) {
        model.addAttribute("loginError", "Invalid username or password");

        return "user-login";
    }

}
