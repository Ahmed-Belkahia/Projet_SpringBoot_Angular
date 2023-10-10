package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Post;
import com.example.techmasterpi.domain.Rating;
import com.example.techmasterpi.domain.Tag;
import com.example.techmasterpi.domain.User;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IPostService {

    Post savePost(Post post) throws Exception;
    public Post addPost(Post p, @NonNull HttpServletRequest request)throws MessagingException;

    public void updatePost(Post p);

    public String deletePost(int idp);

    public List<Post> allPost();

    Post retrievePostById(Integer postId);


    public boolean ratePost(Rating rating);

    public void follow(int idp, int idu);

    public void unfollow(int idp, int idu);
    public  Map<Integer, Post> topPosts(int days);
    public List<Post> findTagPosts(Tag t);

    public List<Post> retrieveByDateAsc();
    public List<Post> retrieveByDateDesc();

    Post getById(Integer offreid);
}