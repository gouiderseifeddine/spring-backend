package com.nour.camping.service;

import com.nour.camping.entity.Commentaire;

import java.util.List;
import java.util.Optional;

public interface CommentaireService {
    Commentaire createCommentaire(Commentaire commentaire);
    Optional<Commentaire> getCommentaireById(Long id);
    List<Commentaire> getCommentsForCamping(Long campingId);
    void deleteCommentaire(Long id);
}
