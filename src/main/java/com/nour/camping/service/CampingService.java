package com.nour.camping.service;

import com.nour.camping.entity.Camping;
import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface CampingService {
    Camping createCamping(Camping camping);
    Optional<Camping> getCampingById(Long id);
    List<Camping> getAllCampings();
    Camping updateCamping(Long id, Camping camping);
    void deleteCamping(Long id);

    Evaluation addEvaluationToCamping(Long campingId, Evaluation evaluation);
    List<Evaluation> getEvaluationsForCamping(Long campingId);

    Activity addActivityToCamping(Long campingId, Activity activity);
    List<Activity> getActivitiesForCamping(Long campingId);

    List<Camping> filterByCampingLocation(String location);
    List<Camping> filterByEvaluations();
    List<Camping> filterByCommentaires();
}
