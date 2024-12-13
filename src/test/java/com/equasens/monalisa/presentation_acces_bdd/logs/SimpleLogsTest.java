package com.equasens.monalisa.presentation_acces_bdd.logs;

import com.equasens.monalisa.presentation_acces_bdd.common.context.AbstractTestWithData;
import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import com.equasens.monalisa.presentation_acces_bdd.repository.PostRepository;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
})
public class SimpleLogsTest extends AbstractTestWithData {

    @Autowired
    private PostRepository postRepository;

    @Random
    private Post post;

    @Test
    void itShouldLogJDBCRequests() {
        postRepository.save(post);
        postRepository.findById(post.getId());
    }
}
