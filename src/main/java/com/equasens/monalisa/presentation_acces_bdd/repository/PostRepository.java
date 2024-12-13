package com.equasens.monalisa.presentation_acces_bdd.repository;

import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
