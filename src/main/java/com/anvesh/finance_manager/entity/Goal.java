package com.anvesh.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String goalName;

    @Column(
            nullable = false
    )
    private Double targetAmount;

    // SAVED AMOUNT
    @Column(
            nullable = false
    )
    private Double savedAmount = 0.0;

    @Column(
            nullable = false
    )
    private LocalDate targetDate;

    // GOAL CREATION DATE
    private LocalDate startDate;

    // USER RELATION
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
            "transactions",
            "password"
    })
    private User user;

    // AUTO SET START DATE
    @PrePersist
    public void prePersist() {

        if (
                startDate == null
        ) {

            startDate =
                    LocalDate.now();
        }

        if (
                savedAmount == null
        ) {

            savedAmount = 0.0;
        }
    }
}