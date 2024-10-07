package com.ashu.blogapp.Services;

import com.ashu.blogapp.Payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);

}
