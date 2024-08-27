package com.nour.camping.service;

import com.nour.camping.entity.Activity;
import com.nour.camping.entity.Camping;
import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Rating;
import com.nour.camping.repository.ActivityRepository;
import com.nour.camping.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, RatingRepository ratingRepository) {
        this.activityRepository = activityRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        activity.setId(id);
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public Rating addRatingToActivity(Long activityId, Rating rating) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            Optional<Rating> existingRating = ratingRepository.findByRater_IdAndActivityRatedId(rating.getRater().getId(), activityId);
            if (existingRating.isPresent()) {
                throw new IllegalArgumentException("User has already evaluated this camping.");
            }
            rating.setActivityRated(activity);
            return ratingRepository.save(rating);
        } else {
            throw new IllegalArgumentException("Activity not found.");
        }
    }

    @Override
    public List<Rating> getRatingsForActivity(Long activityId) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        return activityOptional.map(Activity::getRatings).orElseThrow(() -> new IllegalArgumentException("Activity not found."));
    }

    @Override
    public List<Activity> filterActivitiesByDuration() {
        List<Activity> activities = activityRepository.findAll();
        activities.sort(Comparator.comparing(Activity::getActivityDuration).reversed());
        return activities;
    }

    @Override
    public List<Activity> filterActivitiesByRating() {
        List<Activity> activities = activityRepository.findAll();
        activities.sort((a1, a2) -> Integer.compare(a2.getRatings().size(), a1.getRatings().size()));
        return activities;
    }
}
