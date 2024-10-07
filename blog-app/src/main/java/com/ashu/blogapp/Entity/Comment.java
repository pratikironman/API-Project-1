package com.ashu.blogapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder

@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    // TODO create relation between user and comments (1 user can do multiple comments)

    @ManyToOne
    @JoinColumn(
            name = "post_Id"
    )
    private Post post;

}
