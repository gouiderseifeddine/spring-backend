package com.nour.camping.controller;

import com.nour.camping.entity.*;
import com.nour.camping.repository.CampingRepository;
import com.nour.camping.repository.UserRepository;
import com.nour.camping.service.CampingService;
import com.nour.camping.service.CommentaireService;
import com.nour.camping.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campings")
public class CampingController {

    private final CampingService campingService;
    private final CommentaireService commentaireService;
    private final UserRepository userRepository;
    private final CampingRepository campingRepository;
    private final EvaluationService evaluationService;

    @Autowired
    public CampingController(CampingService campingService, CommentaireService commentaireService,UserRepository userRepository,CampingRepository campingRepository,EvaluationService evaluationService) {
        this.campingService = campingService;
        this.commentaireService = commentaireService;
        this.userRepository=userRepository;
        this.campingRepository=campingRepository;
        this.evaluationService=evaluationService;
    }

    @PostMapping
    public ResponseEntity<Camping> createCamping(@RequestBody Camping camping) {
        Camping createdCamping = campingService.createCamping(camping);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCamping);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camping> getCampingById(@PathVariable Long id) {
        Optional<Camping> camping = campingService.getCampingById(id);
        return camping.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Camping>> getAllCampings() {
        List<Camping> campings = campingService.getAllCampings();
        return ResponseEntity.ok(campings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camping> updateCamping(@PathVariable Long id, @RequestBody Camping camping) {
        Camping updatedCamping = campingService.updateCamping(id, camping);
        return ResponseEntity.ok(updatedCamping);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamping(@PathVariable Long id) {
        campingService.deleteCamping(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{campingId}/evaluations")
    public ResponseEntity<Evaluation> addEvaluation(@PathVariable Long campingId, @RequestBody Evaluation evaluation, @RequestParam Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpressionException("User not found with id " + userId));
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new ExpressionException("Camping not found with id " + campingId));
        evaluation.setEvaluator(user);
        evaluation.setCamping(camping);

        Evaluation savedEvaluation = evaluationService.createEvaluation(evaluation);

        return ResponseEntity.ok(savedEvaluation);
    }


    @GetMapping("/{campingId}/evaluations")
    public ResponseEntity<List<Evaluation>> getEvaluations(@PathVariable Long campingId) {
        try {
            List<Evaluation> evaluations = campingService.getEvaluationsForCamping(campingId);
            return ResponseEntity.ok(evaluations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{campingId}/comments")
    public ResponseEntity<Commentaire> createCommentaire(@PathVariable Long campingId, @RequestBody Commentaire newComment, @RequestParam Long userId) {
        // Fetch the Camping entity
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new ExpressionException("Camping not found with id " + campingId));

        // Fetch the User entity (commentor)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpressionException("User not found with id " + userId));

        // Set the relationships
        newComment.setCamping(camping);
        newComment.setCommentor(user);

        // Save the comment
        Commentaire savedComment = commentaireService.createCommentaire(newComment);

        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/{campingId}/comments")
    public ResponseEntity<List<Commentaire>> getComments(@PathVariable Long campingId) {
        List<Commentaire> comments = commentaireService.getCommentsForCamping(campingId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{campingId}/activities")
    public ResponseEntity<Activity> addActivity(@PathVariable Long campingId, @RequestBody Activity activity) {
        try {
            Activity createdActivity = campingService.addActivityToCamping(campingId, activity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{campingId}/activities")
    public ResponseEntity<List<Activity>> getActivities(@PathVariable Long campingId) {
        try {
            List<Activity> activities = campingService.getActivitiesForCamping(campingId);
            return ResponseEntity.ok(activities);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/filterByCampingLocation")
    public ResponseEntity<List<Camping>> filterByCampingLocation(@RequestParam String campingLocation) {
        List<Camping> campings = campingService.filterByCampingLocation(campingLocation);
        return ResponseEntity.ok(campings);
    }

    @GetMapping("/filterByEvaluations")
    public ResponseEntity<List<Camping>> filterByEvaluations() {
        List<Camping> campings = campingService.filterByEvaluations();
        return ResponseEntity.ok(campings);
    }

    @GetMapping("/filterByCommentaires")
    public ResponseEntity<List<Camping>> filterByCommentaires() {
        List<Camping> campings = campingService.filterByCommentaires();
        return ResponseEntity.ok(campings);
    }
}
