package com.nour.camping.repository;

import com.nour.camping.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findByEvaluator_IdAndCamping_Id(Long evaluatorId, Long campingId);
}
