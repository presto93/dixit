package com.yoon.dixit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yoon.dixit.user.repository")
public class DixitApplication {

    public static void main(String[] args) {
        SpringApplication.run(DixitApplication.class, args);
    }

}
