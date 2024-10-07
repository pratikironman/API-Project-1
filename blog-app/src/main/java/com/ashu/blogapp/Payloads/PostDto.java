package com.ashu.blogapp.Payloads;

import com.ashu.blogapp.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {

    private Integer postId;
    // we can also get postId & customerId as these attributes are available in their dto's classes
//    private Integer categoryId;
//    private Integer id;

    private String title;

    private String content;

    private Date addedDate;

    private String imageName;

    private CategoryDto category;

    private UserDto user;


    // this is done coz as well fetch post so comments related o that post will come along, so we don't need to fetch comments of particular post separately
    // also if we want to do pagination nd sorting for comments so we need to make entirely separate api of comment like others

//    private Set<CommentDto> comment = new HashSet<>();
//    private Comment comment;
    private List<CommentDto> comments = new ArrayList<>();



}
