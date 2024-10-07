package com.ashu.blogapp.Repository;

import com.ashu.blogapp.Entity.Category;
import com.ashu.blogapp.Entity.Post;
import com.ashu.blogapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {


    // custom method we created
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    // @Query("select p from Post p where p.title like : key") -- "key" is dynamic value we'll get from URL like "%keyword%"
    // as we are using JPQL query
    // List<Post> findByTitleContaining(@Param("key") String title);
    List<Post> findByTitleContaining(String title);
}
