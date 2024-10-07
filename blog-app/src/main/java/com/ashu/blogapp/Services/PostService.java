package com.ashu.blogapp.Services;

import com.ashu.blogapp.Entity.Post;
import com.ashu.blogapp.Payloads.PostDto;
import com.ashu.blogapp.Payloads.PostResponse;

import java.util.List;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto , Integer userId, Integer categoryId);

    // update
    PostDto updatePostById(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get
    //getting all post
//    List<PostDto> getAllPost(Integer pageNumber, Integer pageSize); ---- Normal pagination method
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection); //--- Method including Postresponse class

    //getting individual Post
    PostDto getPostById(Integer postId);

    //-----------

    //getting all post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    //getting all post by users
    List<PostDto> getPostByUser(Integer userId);

    //--- custom method impl
    // search post consisting specific keyword
    List<PostDto> searchPost(String keyword);



}
