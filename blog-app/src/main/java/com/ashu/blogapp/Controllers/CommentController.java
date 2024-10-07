package com.ashu.blogapp.Controllers;

import com.ashu.blogapp.Payloads.APIResponse;
import com.ashu.blogapp.Payloads.CommentDto;
import com.ashu.blogapp.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")

public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){

        CommentDto createComment = this.commentService.createComment(commentDto, postId);

        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<APIResponse>(new APIResponse("Comment Deleted Successfully !!", true), HttpStatus.OK);
    }


}
