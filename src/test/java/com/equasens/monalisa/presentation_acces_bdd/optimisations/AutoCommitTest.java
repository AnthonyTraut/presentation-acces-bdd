package com.equasens.monalisa.presentation_acces_bdd.optimisations;

import com.equasens.monalisa.presentation_acces_bdd.common.context.AbstractTestWithData;
import com.equasens.monalisa.presentation_acces_bdd.model.Post;
import com.equasens.monalisa.presentation_acces_bdd.repository.PostRepository;
import com.equasens.monalisa.presentation_acces_bdd.util.DataSourceUtils;
import io.github.glytching.junit.extension.random.Random;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@ActiveProfiles("auto_commit_disabled")
public class AutoCommitTest extends AbstractTestWithData {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DataSource dataSource;

    @Random
    private Post post;

    @Test
    void itShouldSavePostWithoutAutoCommitInTransaction() {
        DataSourceUtils.logNumberOfActivesConnections();
        transactionTemplate.execute(status -> {
            DataSourceUtils.logNumberOfActivesConnections();
            assertThat(DataSourceUtils.getNumberOfActivesConnections()).isEqualTo(0);

            postRepository.save(post);

            DataSourceUtils.logNumberOfActivesConnections();
            assertThat(DataSourceUtils.getNumberOfActivesConnections()).isEqualTo(1);
            return null;
        });

        transactionTemplate.execute(status -> {
            assertThat(postRepository.findById(post.getId())).isNotEmpty();

            return null;
        });
    }
}
