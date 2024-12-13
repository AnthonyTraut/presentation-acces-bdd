package com.equasens.monalisa.presentation_acces_bdd.common.context;

import com.equasens.monalisa.presentation_acces_bdd.common.extension.CustomRandomExtension;
import com.equasens.monalisa.presentation_acces_bdd.util.SpringContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(CustomRandomExtension.class)
@Sql(scripts = "/schema_partie1.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AbstractTestWithData extends AbstractTest {

    @AfterEach
    public void resetDatabase() {
        SpringContext.getBean(JdbcTemplate.class).execute("DROP SCHEMA public CASCADE;CREATE SCHEMA public;COMMIT;");
    }
}
