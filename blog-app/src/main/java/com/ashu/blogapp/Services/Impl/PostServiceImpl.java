package com.ashu.blogapp.Services.Impl;

import com.ashu.blogapp.Entity.Category;
import com.ashu.blogapp.Entity.Post;
import com.ashu.blogapp.Entity.User;
import com.ashu.blogapp.Exceptions.ResourceNotFoundException;
import com.ashu.blogapp.Payloads.PostDto;
import com.ashu.blogapp.Payloads.PostResponse;
import com.ashu.blogapp.Repository.CategoryRepo;
import com.ashu.blogapp.Repository.PostRepo;
import com.ashu.blogapp.Repository.UserRepo;
import com.ashu.blogapp.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        //fetching foreign key attributes (UserRepo, CategoryRepo)
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        // conversion using modelMapper
        Post post = this.modelMapper.map(postDto, Post.class);

        // Manually setting below 2 attributes as this 2 attribute are not included in PostDto class
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        // Manually setting below 2 foreignKey_attributes as this 2 attribute are not included in PostDto class
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        this.postRepo.delete(post);

    }

    @Override
    // public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize) --- Normal pagination method
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) // --- Method including Post response class
    {
        // Pagination & sorting ----  (getting pageNumber & pageSize dynamically from client through URL)
        // (http://localhost:8080/api/posts?pageNumber=0&pageSize=5&sortBy=postId&sortDirection=asc)
//        int pageSize = 5;
//        int pageNumber = 1;

        // creating pageable object "Pageable p"
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        } else{
            sort = Sort.by(sortBy).descending();
        }
        // we can also use "ternary operation" in case of "If-else" done above
        //Sort sort= sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        // using pageable object we would get "Page<>"
        Page<Post> postPagepost = this.postRepo.findAll(p);

        //So to get actual "list<>" out from "Page<>" we would use ".getcontent()"
        List<Post> posts = postPagepost.getContent();
//        List<Post> posts= this.postRepo.findAll(); --- without Pagination method(normal method implementation)

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        // setting PostResponse class values
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPagepost.getNumber());
        postResponse.setPageSize(postPagepost.getSize());
        postResponse.setTotalElements(postPagepost.getTotalElements());
        postResponse.setTotalPages(postPagepost.getTotalPages());
        postResponse.setLastPage(postPagepost.isLast());



        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    // TODO -------------- Pagination & sorting along with using PageResponse need to be done------------------//
    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        List<Post> posts = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    // TODO -------------- Pagination & sorting with using PageResponse need to be done------------------//
    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        // List<Post> posts = this.postRepo.findByTitleContaining("%"+ keyword +"%"); -- using JPQL query

        List<PostDto> postDtos= posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

}
