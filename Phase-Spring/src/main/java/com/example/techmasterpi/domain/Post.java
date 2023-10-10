package com.example.techmasterpi.domain;


import com.example.techmasterpi.model.Category;
import com.example.techmasterpi.model.State;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Post {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer postId;

    @Column
    //@NotBlank(message = "the Title of tte post is required!")

    private String title;
    @Column
    private String image;
    @Lob
    @Column(name = "post_content", columnDefinition = "LONGTEXT")

    //@NotBlank(message = "the Content of tte post is required!")
    private String postContent;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postdate;

    @Column
    private Integer countLike;


    @Column
    @Enumerated(EnumType.STRING)
    private State state;




    @OneToMany(cascade = CascadeType.ALL,mappedBy = "postComment")
    @JsonIgnore
    private Set<Comment> comments;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_post_id")
    @JsonIgnore
    private User userPost;
    private String followers;




    @ManyToMany(cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Tag> tags;
    @JsonIgnore
    @OneToMany(mappedBy="post", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Rating> listRating;



}