package com.nour.camping.service;

import com.nour.camping.entity.Evaluation;
import com.nour.camping.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        Optional<Evaluation> existingEvaluation = getEvaluationByUserAndCamping(evaluation.getEvaluator().getId(), evaluation.getCamping().getId());
        if (existingEvaluation.isPresent()) {
            throw new IllegalArgumentException("User has already evaluated this camping.");
        }
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Override
    public Optional<Evaluation> getEvaluationByUserAndCamping(Long evaluatorId, Long campingId) {
        return evaluationRepository.findByEvaluator_IdAndCamping_Id(evaluatorId, campingId);
    }

    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
