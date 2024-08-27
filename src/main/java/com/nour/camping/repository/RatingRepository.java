package com.nour.camping.repository;

import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByRater_IdAndActivityRatedId(Long raterId, Long activityId);
}
