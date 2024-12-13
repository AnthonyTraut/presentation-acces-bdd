package com.equasens.monalisa.presentation_acces_bdd.logs;

import com.equasens.monalisa.presentation_acces_bdd.common.context.AbstractTestWithData;
import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import com.equasens.monalisa.presentation_acces_bdd.repository.PostRepository;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("logs_jdbc_enabled")
public class ProxyDataSourceLogsTest extends AbstractTestWithData {

    @Autowired
    private PostRepository postRepository;

    @Random
    private Post post;

    @Test
    void itShouldLogJDBCRequestsWithProxyDataSource() {
        postRepository.save(post);
        postRepository.findById(post.getId());
    }
}
