package com.ashu.blogapp.Services.Impl;

import com.ashu.blogapp.Entity.Comment;
import com.ashu.blogapp.Entity.Post;
import com.ashu.blogapp.Exceptions.ResourceNotFoundException;
import com.ashu.blogapp.Payloads.CommentDto;
import com.ashu.blogapp.Repository.CommentRepo;
import com.ashu.blogapp.Repository.PostRepo;
import com.ashu.blogapp.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        this.commentRepo.delete(comment);

    }
}
