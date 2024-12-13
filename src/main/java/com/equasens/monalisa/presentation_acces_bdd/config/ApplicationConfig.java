package com.equasens.monalisa.presentation_acces_bdd.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.equasens.monalisa.presentation_acces_bdd.service",
        "com.equasens.monalisa.presentation_acces_bdd.controller"
})
public class ApplicationConfig {
}
