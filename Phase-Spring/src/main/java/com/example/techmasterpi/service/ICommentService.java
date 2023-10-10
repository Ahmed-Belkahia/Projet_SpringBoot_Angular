package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Comment;
import com.example.techmasterpi.domain.Post;

import java.io.IOException;
import java.util.List;

public interface ICommentService  {
    public void addComment(Comment c ) throws IOException;


    public void updateComment(Comment c);
    public String deleteComment(int idc);
    public List<Comment> comments();

    Comment retrieveCommentById(Integer commentId);
    public List<Comment> comments(Post p);



}