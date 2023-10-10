package com.example.techmasterpi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


import java.util.Set;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Tag {

    @Id
    @Column(nullable = false, updatable = false)


    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int tagId;

    @Column(unique = true)
    private String name ;

    @ManyToMany(cascade=CascadeType.ALL,mappedBy = "tags")
    private Set<Post> posts;


}
