package com.equasens.monalisa.presentation_acces_bdd.optimisations;

import com.equasens.monalisa.presentation_acces_bdd.common.context.AbstractTestWithData;
import com.equasens.monalisa.presentation_acces_bdd.model.Forum;
import com.equasens.monalisa.presentation_acces_bdd.repository.ForumRepository;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("open_in_view_disabled")
public class OpenInViewTest extends AbstractTestWithData {

    @Random
    private Forum forum;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    void itShouldGetForumWithoutOpenInView() throws Exception {
        forumRepository.save(forum);
        mvc.perform(get("/forum/{id}", forum.getId()))
                .andExpect(status().is5xxServerError());
    }
}
