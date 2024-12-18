package com.raj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.raj.entity.User;
import com.raj.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User saveUser(User user) {
        // Set role without encoding password before saving the user
        user.setRole("ROLE_USER");
        return userRepo.save(user);
    }

    @Override
    public boolean existEmailCheck(String email) {
        return userRepo.existsByEmail(email);
    }

    // Method to remove session message
    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession();
        session.removeAttribute("msg");
    }

    @Override// Implementing the login method without password encoding
    public User userLogin(String email, String password) {
        // Fetch user from the repository using email
        User user = userRepo.findByEmail(email);

        // If user is found and passwords match, return the user object
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
   
        // If credentials are invalid, return null
        return null;
    }
    
  

        @Override
        public User findByEmail(String email) {
            return userRepo.findByEmail(email);
        }

//        @Override
//        public boolean checkPassword(User user, String password) {
//            // If passwords are hashed, add hash verification logic here.
//            return user.getPassword().equals(password);
//        }
}
