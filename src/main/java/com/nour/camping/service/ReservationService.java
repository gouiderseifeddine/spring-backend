package com.nour.camping.service;

import com.nour.camping.entity.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Optional<Reservation> getReservationById(Long id);
    List<Reservation> getAllReservations();
    Reservation updateReservation(Long id, Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> getReservationsByUserId(Long userId);
}
