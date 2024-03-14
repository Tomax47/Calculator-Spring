package com.calculator.cal.service.impl;

import com.calculator.cal.dto.UserForm;
import com.calculator.cal.model.User;
import com.calculator.cal.repository.UserRepo;
import com.calculator.cal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.calculator.cal.mapper.UserMapper.mapToUser;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(UserForm userForm) {
        User user = User.builder()
                .email(userForm.getEmail())
                .username(userForm.getUsername())
                .password(encoder.encode(userForm.getPassword()))
                .role("USER")
                .build();

        userRepo.save(user);
    }
}
