package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




import javax.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findByPassword(String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedloginattempts = u.failedloginattempts+1 WHERE u.email = ?1")
    int FailedLogin(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = FALSE WHERE u.email = ?1")
    int noEnableUser(String email);


    @Modifying
    @Query("UPDATE User u SET u.timeBlocked = :blockedTime WHERE u.userid = :userId")
    void updateBlockedTime(@Param("userId") int userId, @Param("blockedTime") LocalDateTime blockedTime);




    @Query("SELECT u, COUNT(d) AS deliveryCount FROM Delivery d JOIN d.userDelevery u WHERE d.state = 'Payed' GROUP BY u ORDER BY deliveryCount DESC")
    List<Object[]> findUserByBestDelivery();

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.usertax")
    List<User> findAllWithTaxes();

    User findByPhone(int phone);

}
