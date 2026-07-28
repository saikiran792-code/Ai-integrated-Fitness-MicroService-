package com.fitness.activityservice.repo;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Activityrepo  extends MongoRepository<Activity,String> {
    List<Activity> findByUserid(String userid);
}

