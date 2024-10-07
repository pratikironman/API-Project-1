package com.ashu.blogapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addedDate;



    // relations between category & Posts
    // "@ManyToOne" is used here for category & post as it indicated bi-direction relation between entities... for eg. "as 1 category can have many posts, likewise many post may belong to single category & similar goes for user also"

    @ManyToOne
    // join column is primary key of entity
    @JoinColumn(
            name = "category_id"
    )
    private Category category;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @OneToMany(
            mappedBy = "post", cascade = CascadeType.ALL
    )
//    private Set<Comment> comments = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();



}
