package com.nour.camping.controller;

import com.nour.camping.entity.Commentaire;
import com.nour.camping.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentaireController {

    private final CommentaireService commentaireService;

    @Autowired
    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @PostMapping
    public ResponseEntity<Commentaire> createCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire createdCommentaire = commentaireService.createCommentaire(commentaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentaire);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaireById(@PathVariable Long id) {
        Optional<Commentaire> commentaire = commentaireService.getCommentaireById(id);
        return commentaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/camping/{campingId}")
    public ResponseEntity<List<Commentaire>> getCommentsForCamping(@PathVariable Long campingId) {
        List<Commentaire> comments = commentaireService.getCommentsForCamping(campingId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
