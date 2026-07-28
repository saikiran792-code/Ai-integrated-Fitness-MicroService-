package com.fitness.activityservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@EnableMongoAuditing
public class MongoDebug {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/fitnessActivity");
        MongoClient mongoClient = MongoClients.create(connectionString);
        return new SimpleMongoClientDatabaseFactory(mongoClient, "fitnessActivity");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    CommandLineRunner runner(MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println("Database: " + mongoTemplate.getDb().getName());
            System.out.println("Collections: " + mongoTemplate.getCollectionNames());
        };
    }
}