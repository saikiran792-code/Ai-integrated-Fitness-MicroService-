package com.fitness.activityservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DebugConfig {

    @Autowired
    private Environment env;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void check() {
        System.out.println("==================================");
        System.out.println("URI = " + env.getProperty("spring.data.mongodb.uri"));
        System.out.println("DATABASE = " + env.getProperty("spring.data.mongodb.database"));
        System.out.println("MongoTemplate DB = " + mongoTemplate.getDb().getName());
        System.out.println("==================================");
    }
}