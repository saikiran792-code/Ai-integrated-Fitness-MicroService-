package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repo.Activityrepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final Activityrepo activityrepo;
    private final UserValidationService validationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;


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
                 Activity savedActivity=activityrepo.save(activity);
//                 publish to the rabbitmq
       try{
           rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);

       }catch (Exception e){
        log.error("Failed to publish");
       }
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
