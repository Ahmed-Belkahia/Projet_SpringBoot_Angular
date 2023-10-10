package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.Notification;
import com.example.techmasterpi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> getByUser(User user);
}
