package com.ashu.blogapp.Repository;

import com.ashu.blogapp.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
