package com.nour.camping.service;

import com.nour.camping.entity.Camping;
import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Activity;
import com.nour.camping.repository.CampingRepository;
import com.nour.camping.repository.CommentaireRepository;
import com.nour.camping.repository.EvaluationRepository;
import com.nour.camping.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampingServiceImpl implements CampingService {

    private final CampingRepository campingRepository;
    private final EvaluationRepository evaluationRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public CampingServiceImpl(CampingRepository campingRepository, EvaluationRepository evaluationRepository, ActivityRepository activityRepository) {
        this.campingRepository = campingRepository;
        this.evaluationRepository = evaluationRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public Camping createCamping(Camping camping) {
        return campingRepository.save(camping);
    }

    @Override
    public Optional<Camping> getCampingById(Long id) {
        return campingRepository.findById(id);
    }

    @Override
    public List<Camping> getAllCampings() {
        return campingRepository.findAll();
    }

    @Override
    public Camping updateCamping(Long id, Camping camping) {
        camping.setId(id);
        return campingRepository.save(camping);
    }

    @Override
    public void deleteCamping(Long id) {
        campingRepository.deleteById(id);
    }

    @Override
    public Evaluation addEvaluationToCamping(Long campingId, Evaluation evaluation) {
        Optional<Camping> campingOptional = campingRepository.findById(campingId);
        if (campingOptional.isPresent()) {
            Camping camping = campingOptional.get();
            Optional<Evaluation> existingEvaluation = evaluationRepository.findByEvaluator_IdAndCamping_Id(evaluation.getEvaluator().getId(), campingId);
            if (existingEvaluation.isPresent()) {
                throw new IllegalArgumentException("User has already evaluated this camping.");
            }
            evaluation.setCamping(camping);
            return evaluationRepository.save(evaluation);
        } else {
            throw new IllegalArgumentException("Camping not found.");
        }
    }

    @Override
    public List<Evaluation> getEvaluationsForCamping(Long campingId) {
        Optional<Camping> campingOptional = campingRepository.findById(campingId);
        return campingOptional.map(Camping::getEvaluations).orElseThrow(() -> new IllegalArgumentException("Camping not found."));
    }

    @Override
    public Activity addActivityToCamping(Long campingId, Activity activity) {
        Optional<Camping> campingOptional = campingRepository.findById(campingId);
        if (campingOptional.isPresent()) {
            Camping camping = campingOptional.get();
            activity.setCampingSession(camping);
            return activityRepository.save(activity);
        } else {
            throw new IllegalArgumentException("Camping not found.");
        }
    }

    @Override
    public List<Activity> getActivitiesForCamping(Long campingId) {
        Optional<Camping> campingOptional = campingRepository.findById(campingId);
        return campingOptional.map(Camping::getActivities).orElseThrow(() -> new IllegalArgumentException("Camping not found."));
    }

    @Override
    public List<Camping> filterByCampingLocation(String location) {
        return campingRepository.findByCampingLocation(location);
    }

    @Override
    public List<Camping> filterByEvaluations() {
        return campingRepository.findAllByOrderByEvaluationsDesc();
    }

    @Override
    public List<Camping> filterByCommentaires() {
        return campingRepository.findAllByOrderByCommentsDesc();
    }
}
