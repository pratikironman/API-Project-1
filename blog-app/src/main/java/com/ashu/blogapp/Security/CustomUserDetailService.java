package com.ashu.blogapp.Security;

import com.ashu.blogapp.Entity.User;
import com.ashu.blogapp.Exceptions.ResourceNotFoundException;
import com.ashu.blogapp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading users from DB by userName(Email in our case),
        // So to interact with DB we need JPA to interact with DB
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "UserEmail :" + username, 0));

        return user;
    }

}
