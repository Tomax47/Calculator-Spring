package com.calculator.cal.security.details;

import com.calculator.cal.model.User;
import com.calculator.cal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("customDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get().getUsername());
            System.out.println(userOptional.get().getPassword());
            System.out.println("************");
            return new UserDetailsImpl(userOptional.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
