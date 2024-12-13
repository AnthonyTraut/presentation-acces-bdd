package com.equasens.monalisa.presentation_acces_bdd.controller;

import com.equasens.monalisa.presentation_acces_bdd.model.Forum;
import com.equasens.monalisa.presentation_acces_bdd.service.ForumService;
import com.equasens.monalisa.presentation_acces_bdd.util.DataSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("forum")
@Slf4j
public class ForumController {

    private final ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/{id}")
    public Forum save(@PathVariable("id") final Long id) {
        final Optional<Forum> forum = forumService.findById(id);
        DataSourceUtils.logNumberOfActivesConnections();
        return forum.get();
    }
}
