package com.ssafy.paginationpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PaginationPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaginationPracticeApplication.class, args);
    }
}
