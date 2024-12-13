package com.equasens.monalisa.presentation_acces_bdd.service.impl;

import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import com.equasens.monalisa.presentation_acces_bdd.repository.PostRepository;
import com.equasens.monalisa.presentation_acces_bdd.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Post save(final Post post) {
        return postRepository.save(post);
    }
}
