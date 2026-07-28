package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repo.Activityrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final Activityrepo activityrepo;

    private final UserValidationService validationService;

    public ActivityResponse trackActivity(ActivityRequest request) {
        System.out.println("User Id = " + request.getUserid());
    boolean isValidUser=validationService.validateUser(request.getUserid());
    if(!isValidUser){

        throw new RuntimeException("Invalid user"+request.getUserid());
    }
        System.out.println("Request received: " + request);
        Activity activity=Activity.builder()
                .userid(request.getUserid())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalActivity(request.getAdditionalActivity())
                .build();
        System.out.println("Before Save: " + activity);
        Activity savedActivity=activityrepo.save(activity);
        System.out.println("After Save: " + savedActivity);
        return maptoresponse(savedActivity);
    }
    private ActivityResponse maptoresponse(Activity activity){
        ActivityResponse response=new ActivityResponse();
        response.setId(activity.getId());
        response.setType(activity.getType());
        response.setUserid(activity.getUserid());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setAdditionalActivity(activity.getAdditionalActivity());
        response.setUpdatedAt(activity.getUpdatedAt());
        response.setCreatedAt(activity.getCreatedAt());
        response.setStartTime(activity.getStartTime());
          return response;
    }
    public List<ActivityResponse> getUserActivity(String userId) {
   List<Activity> activities=activityrepo.findByUserid(userId);
   return activities.stream()
           .map(this::maptoresponse)
           .collect(Collectors.toList());
    }


    public ActivityResponse getActivityById(String activityId) {
        return  activityrepo.findById(activityId)
                .map(this::maptoresponse)
                .orElseThrow(() -> new RuntimeException("Id not found"));
    }
}
