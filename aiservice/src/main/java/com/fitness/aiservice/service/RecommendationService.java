package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.Recommendationrepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final Recommendationrepo recommendationrepo;

    public List<Recommendation> getUserRecommendation(String userId) {
       return recommendationrepo.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationrepo.findByActivityId(activityId)
                .orElseThrow(()-> new RuntimeException(" No Recommendations is found for this Activity"));
    }
}
