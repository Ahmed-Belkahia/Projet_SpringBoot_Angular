package com.example.techmasterpi.service;



import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import com.example.techmasterpi.domain.Comment;
import com.example.techmasterpi.domain.Notification;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.repos.CommentRepository;
import com.example.techmasterpi.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    CommentRepository commentRepository;


    @Override
    @Transactional
    public List<String> showMyNotif(User user) {
        List<Notification> notifications = notificationRepository.getByUser(user);

        List<Comment> comments = commentRepository.findAll();
        List<String> messages = new ArrayList<String>();
        for (Notification n : notifications) {
            //likes.add(likesRepository.getLikesByNotif(n));

            for(Comment comment : comments){
                Duration dur = Duration.between(comment.getCommentDate(),LocalDateTime.now());
                long millis = dur.toMillis();
                long value = TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));

                if(comment.getNotification().equals(n))
                {
                    if(value < 60)
                        messages.add(comment.getUser().getUsername()+" commented your post "+
                                String.format("%02d minutes ago",
                                        TimeUnit.MILLISECONDS.toMinutes(millis)
                                                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
                    else
                        messages.add(comment.getUser().getUsername()+" commented your post");
                }



            }
        }



        return messages;
    }

}