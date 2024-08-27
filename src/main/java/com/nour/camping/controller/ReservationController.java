package com.nour.camping.controller;

import com.nour.camping.entity.Camping;
import com.nour.camping.entity.Evaluation;
import com.nour.camping.entity.Reservation;
import com.nour.camping.entity.User;
import com.nour.camping.repository.CampingRepository;
import com.nour.camping.repository.UserRepository;
import com.nour.camping.service.CampingService;
import com.nour.camping.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;
    private final CampingRepository campingRepository;

    @Autowired
    public ReservationController(ReservationService reservationService,UserRepository userRepository,CampingRepository campingRepository) {
        this.reservationService = reservationService;
        this.userRepository=userRepository;
        this.campingRepository=campingRepository;
    }

    @PostMapping("/{campingId}/reservation")
    public ResponseEntity<Reservation> createReservation(@PathVariable Long campingId,@RequestBody Reservation reservation, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExpressionException("User not found with id " + userId));
        Camping camping = campingRepository.findById(campingId)
                .orElseThrow(() -> new ExpressionException("Camping not found with id " + campingId));
        reservation.setUserReserving(user);
        reservation.setCamping(camping);

        Reservation savedReservation = reservationService.createReservation(reservation);

        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }
}
