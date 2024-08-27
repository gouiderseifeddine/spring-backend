package com.nour.camping.service;

import com.nour.camping.entity.Commentaire;
import com.nour.camping.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    private final CommentaireRepository commentaireRepository;

    @Autowired
    public CommentaireServiceImpl(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    @Override
    public Commentaire createCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public Optional<Commentaire> getCommentaireById(Long id) {
        return commentaireRepository.findById(id);
    }

    @Override
    public List<Commentaire> getCommentsForCamping(Long campingId) {
        return commentaireRepository.findByCamping_Id(campingId);
    }

    @Override
    public void deleteCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }
}
