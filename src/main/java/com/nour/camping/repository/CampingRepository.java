package com.nour.camping.repository;

import com.nour.camping.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Long> {
    Optional<Camping> findById(Long Id);

    // Custom query methods for filtering
    List<Camping> findByCampingLocation(String location);
    List<Camping> findAllByOrderByEvaluationsDesc();
    List<Camping> findAllByOrderByCommentsDesc();
}
