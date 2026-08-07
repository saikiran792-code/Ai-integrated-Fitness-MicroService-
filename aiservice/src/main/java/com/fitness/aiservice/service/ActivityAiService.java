package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;
    String generateRecommendations(Activity activity){
        String propmt=createPropmtForActivity(activity);
        String aiResponse=geminiService.getAnswer(propmt);
        log.info("Response for ai:{}"+aiResponse);
        return aiResponse;
    }

    private String createPropmtForActivity(Activity activity) {
        return String.format("""
            You are an AI fitness activity analysis assistant.

            Analyze the user's fitness activity data and provide a useful, concise fitness analysis.

            Activity Data:
            - Type: %s
            - Duration: %d minutes
            - Calories Burned: %d
            - Start Time: %s
            - Additional Activity Data: %s

            Your responsibilities:

            1. Analyze the activity type, duration, and calories burned.
            2. Determine whether the activity was LOW, MODERATE, or HIGH intensity when enough information is available.
            3. Give a simple explanation of the activity performance.
            4. Identify positive aspects of the activity.
            5. Suggest practical improvements.
            6. Give recommendations for the user's next workout.
            7. Do not invent information that is not present in the activity data.
            8. If some information is missing, base the analysis only on available information.
            9. Keep the response suitable for a fitness application.
            10. Do not provide medical diagnosis or medical treatment advice.
            11. Return ONLY valid JSON.
            12. Do not use Markdown.
            13. Do not add text before or after the JSON.

            Return JSON using exactly this structure:

            {
              "activitySummary": {
                "type": "",
                "durationMinutes": 0,
                "caloriesBurned": 0,
                "intensity": "",
                "performance": ""
              },
              "analysis": {
                "overview": "",
                "positivePoints": [],
                "areasToImprove": []
              },
              "recommendations": {
                "nextWorkout": "",
                "hydration": "",
                "recovery": ""
              },
              "score": 0,
              "message": ""
            }

            Rules:
            - intensity must be one of: LOW, MODERATE, HIGH, UNKNOWN.
            - score must be an integer from 0 to 100.
            - positivePoints must be an array of strings.
            - areasToImprove must be an array of strings.
            - All fields must be present.
            - Do not expose userid, id, createdAt, or updatedAt.
            - Keep recommendations realistic and general.
            """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalActivity()
        );
    }
}
