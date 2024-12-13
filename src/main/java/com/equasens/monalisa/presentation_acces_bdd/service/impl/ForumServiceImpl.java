package com.equasens.monalisa.presentation_acces_bdd.service.impl;

import com.equasens.monalisa.presentation_acces_bdd.model.Forum;
import com.equasens.monalisa.presentation_acces_bdd.repository.ForumRepository;
import com.equasens.monalisa.presentation_acces_bdd.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

    @Autowired
    public ForumServiceImpl(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Forum> findById(final Long id) {
        return forumRepository.findById(id);
    }
}
