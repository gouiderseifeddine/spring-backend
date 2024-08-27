package com.nour.camping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Date dateReservation;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User userReserving;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "camping_id")
    private Camping camping;
}
