package com.nour.camping.service;

import com.nour.camping.entity.Evaluation;

import java.util.Optional;

public interface EvaluationService {
    Evaluation createEvaluation(Evaluation evaluation);
    Optional<Evaluation> getEvaluationById(Long id);
    Optional<Evaluation> getEvaluationByUserAndCamping(Long evaluatorId, Long campingId);
    void deleteEvaluation(Long id);
}
