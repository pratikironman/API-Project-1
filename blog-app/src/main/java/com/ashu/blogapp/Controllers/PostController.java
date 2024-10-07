package com.ashu.blogapp.Controllers;

import com.ashu.blogapp.UtilClassHelper.AppConstants;
import com.ashu.blogapp.Payloads.APIResponse;
import com.ashu.blogapp.Payloads.PostDto;
import com.ashu.blogapp.Payloads.PostResponse;
import com.ashu.blogapp.Services.FileService;
import com.ashu.blogapp.Services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}") //--------- for getting image path
    private String path;

    //Post - create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable("userId") Integer uid,
            @PathVariable("categoryId") Integer cid){

        PostDto createdPost = this.postService.createPost(postDto, uid, cid);

        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //PUT - update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId){
        PostDto updatedPost = this.postService.updatePostById(postDto,postId);

        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }


    // DELETE - delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);

        return new ResponseEntity<APIResponse>(new APIResponse("Post deleted Successfully !!", true), HttpStatus.OK);
    }


    //GET - get User
    //getting particular user based on Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getByPostId(@PathVariable("postId") Integer pid){

        PostDto post = this.postService.getPostById(pid);

        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    // getting all Users
    // implementing pagination for this method
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            //taking pageNumber & pageSize dynamically through URL
            // (http://localhost:8080/api/posts?pageNumber=0&pageSize=5&sortBy=postId&sortDirection=asc)
            // "defaultValue" is used if data is not present via URL/client then it will by default set as defaultValue mentioned, similarly "required=false" states it not required to give i/p through URL we'll set it bydefault
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)  Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    )
    {
        // List<PostDto> postDtos = this.postService.getAllPost(pageNumber, pageSize, sortBy);
        PostResponse postDtos = this.postService.getAllPost(pageNumber, pageSize, sortBy ,sortDirection);

        return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK);
    }



    //-----------
    //getting all post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer cid){

        List<PostDto> post = this.postService.getPostByCategory(cid);

        return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);
    }


    //getting all post by users
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer uid){

        List<PostDto> post = this.postService.getPostByUser(uid);

        return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);
    }


    //--- custom method impl
    // Search post consisting specific keyword //
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchByTitleContaining(@PathVariable String keywords){
        List<PostDto> res = this.postService.searchPost(keywords);

        return new ResponseEntity<List<PostDto>>(res, HttpStatus.OK);
    }


    //----- implementing method for img
    //Post - img upload Method
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {

        // getting for which postId img is getting uploaded
        PostDto postDto = this.postService.getPostById(postId);

        //uploading img
        String fileName = this.fileService.uploadImage(path, image);


        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePostById(postDto, postId);

        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    //Post - img serving Method means frontEnd developer ca use this api to view img uploaded
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
          @PathVariable("imageName") String imageName,
          HttpServletResponse response
    ) throws IOException
    {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }


}
