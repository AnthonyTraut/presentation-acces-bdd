package com.equasens.monalisa.presentation_acces_bdd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Forum {

    @Id
    @SequenceGenerator(name = "forum_sequence", sequenceName = "forum_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forum_sequence")
    private Long id;

    @Column
    private String titre;
    
    @OneToMany
    @JoinColumn(name = "forum_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Post> posts;
    
}
