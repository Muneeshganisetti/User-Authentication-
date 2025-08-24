package com.muneesh.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.muneesh.repository")
@EntityScan(basePackages = "com.muneesh.entity")
@ComponentScan(basePackages = "com.muneesh")


@SpringBootApplication
public class Project1Application {
    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }

}
