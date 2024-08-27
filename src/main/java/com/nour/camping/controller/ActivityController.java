package com.nour.camping.controller;

import com.nour.camping.entity.*;
import com.nour.camping.repository.ActivityRepository;
import com.nour.camping.repository.UserRepository;
import com.nour.camping.service.ActivityService;
import com.nour.camping.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final RatingService ratingService;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    @Autowired
    public ActivityController(ActivityService activityService, RatingService ratingService, ActivityRepository activityRepository,UserRepository userRepository) {
        this.activityService = activityService;
        this.ratingService = ratingService;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity createdActivity = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        Activity updatedActivity = activityService.updateActivity(id, activity);
        return ResponseEntity.ok(updatedActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{activityId}/ratings")
    public ResponseEntity<Rating> addRatingToActivity(@PathVariable Long activityId, @RequestBody Rating rating, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpressionException("User not found with id " + userId));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ExpressionException("Camping not found with id " + activityId));
        rating.setRater(user);
        rating.setActivityRated(activity);
        Rating savedRating = ratingService.createRating(rating);

        return ResponseEntity.ok(savedRating);
    }

    @GetMapping("/{activityId}/ratings")
    public ResponseEntity<List<Rating>> getRatingsForActivity(@PathVariable Long activityId) {
        try {
            List<Rating> ratings = activityService.getRatingsForActivity(activityId);
            return ResponseEntity.ok(ratings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @GetMapping("/filter/duration")
    public ResponseEntity<List<Activity>> filterActivitiesByDuration() {
        List<Activity> activities = activityService.filterActivitiesByDuration();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/filter/ratings")
    public ResponseEntity<List<Activity>> filterActivitiesByRating() {
        List<Activity> activities = activityService.filterActivitiesByRating();
        return ResponseEntity.ok(activities);
    }
}
