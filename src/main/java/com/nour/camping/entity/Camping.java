package com.nour.camping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Camping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titreCamping;
    private String programmeCamping;
    private String campingLocation;
    private String meetingPlace;
    private Date startDate;
    private Date endDate;

    @JsonIgnore
    @OneToMany(mappedBy = "camping", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @JsonIgnore
    @OneToMany(mappedBy = "camping", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    @JsonIgnore
    @OneToMany(mappedBy = "camping", cascade = CascadeType.ALL)
    private List<Commentaire> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "campingSession", cascade = CascadeType.ALL)
    private List<Activity> activities;
}
