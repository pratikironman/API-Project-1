package com.ashu.blogapp.Repository;

import com.ashu.blogapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    // for security purpose we are interactng with DB using "CustomUserDetailService.class"
    Optional<User> findByEmail(String email);

}
