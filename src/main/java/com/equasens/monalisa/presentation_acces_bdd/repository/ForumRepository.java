package com.equasens.monalisa.presentation_acces_bdd.repository;

import com.equasens.monalisa.presentation_acces_bdd.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
