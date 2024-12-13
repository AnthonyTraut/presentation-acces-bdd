package com.equasens.monalisa.presentation_acces_bdd;

import com.equasens.monalisa.presentation_acces_bdd.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class PresentationAccesBddApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentationAccesBddApplication.class, args);
    }

}
