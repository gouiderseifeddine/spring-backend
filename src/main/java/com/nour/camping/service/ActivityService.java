package com.nour.camping.service;

import com.nour.camping.entity.Activity;
import com.nour.camping.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    Activity createActivity(Activity activity);
    Optional<Activity> getActivityById(Long id);
    List<Activity> getAllActivities();
    Activity updateActivity(Long id, Activity activity);
    void deleteActivity(Long id);

    // Additional methods for handling Ratings
    Rating addRatingToActivity(Long activityId, Rating rating);
    List<Rating> getRatingsForActivity(Long activityId);

    // Methods for filtering
    List<Activity> filterActivitiesByDuration();
    List<Activity> filterActivitiesByRating();
}
