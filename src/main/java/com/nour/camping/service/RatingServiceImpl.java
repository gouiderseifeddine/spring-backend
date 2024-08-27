package com.nour.camping.service;

import com.nour.camping.entity.Activity;
import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Rating;
import com.nour.camping.repository.ActivityRepository;
import com.nour.camping.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    //private final ActivityRepository activityRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ActivityRepository activityRepository) {
        this.ratingRepository = ratingRepository;
        //this.activityRepository = activityRepository;
    }

    @Override
    public Rating createRating(Rating rating) {
        Optional<Rating> existingEvaluation = getRatingsByUserAndActivity(rating.getRater().getId(), rating.getActivityRated().getId());
        if (existingEvaluation.isPresent()) {
            throw new IllegalArgumentException("User has already rated this camping.");
        }
        return ratingRepository.save(rating);
        }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Optional<Rating> getRatingsByUserAndActivity(Long raterId, Long activityId) {
        return ratingRepository.findByRater_IdAndActivityRatedId(raterId, activityId);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}



