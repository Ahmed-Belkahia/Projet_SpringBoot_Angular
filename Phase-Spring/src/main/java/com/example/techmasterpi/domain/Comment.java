package com.example.techmasterpi.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



import java.time.OffsetDateTime;




@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Comment {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer commentId;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentDate;

    //@Column
//private Integer reactCount;
    @Column
    //@NotBlank(message = "the Content of the comment is required!")

    private String commentContent;

    //@Column
    //  private Integer countLike;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private Post postComment;

    @ManyToOne
    @JsonIgnoreProperties("comment")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy="reactComment", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<CommentReact> listReacts;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notif_id", referencedColumnName = "idNotification")
    private Notification notification;

}
