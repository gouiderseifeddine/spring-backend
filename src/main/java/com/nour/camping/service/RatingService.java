package com.nour.camping.service;

import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Rating createRating(Rating rating);
    Optional<Rating> getRatingById(Long id);
    Optional<Rating> getRatingsByUserAndActivity(Long raterId,Long activityId);
    void deleteRating(Long id);
}
