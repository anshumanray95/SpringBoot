package com.raj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raj.entity.User;
import com.raj.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    // Home page mapping
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Register page mapping
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail( String email) {
        return userService.existEmailCheck(email);
    }
    // Save user registration with password and confirm password validation
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {
        boolean emailExists = userService.existEmailCheck(user.getEmail());

        if (!emailExists) {

        // Proceed with registration only if the email is new
        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return "redirect:/signin";
        } else {
            return "redirect:/register";
        }
    }
        return "redirect:/register";
    }

    // Signin page mapping
    @GetMapping("/signin")
    public String signinPage() {
        return "login";  // Return the view for the sign-in page
    }

    // User login handling
    @PostMapping("/userLogin")
    public String userLogin(
        String email,
         String password,
        HttpSession session,
        Model model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("msg", "Email is not registered.");
            return "login"; // Return to the same login page with error
        }

        if (!user.getPassword().equals(password)) { // Or use password hashing if needed
            model.addAttribute("msg", "Incorrect password.");
            model.addAttribute("email", email); // Preserve the email in the form
            return "login"; // Return to the same login page with error
        }

        // Successful login
        session.setAttribute("user", user);
        return "redirect:/user/viewNote"; // Redirect after successful login
    }

}
