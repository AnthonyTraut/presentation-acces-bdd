package com.equasens.monalisa.presentation_acces_bdd.service;

import com.equasens.monalisa.presentation_acces_bdd.model.Forum;

import java.util.Optional;

public interface ForumService {

    Optional<Forum> findById(Long id);
}
