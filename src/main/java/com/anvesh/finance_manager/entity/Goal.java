package com.anvesh.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;

    private Double targetAmount;

    // NEW FIELD
    private Double savedAmount;

    private LocalDate targetDate;

    // GOAL CREATION DATE
    private LocalDate startDate;

    // USER RELATION
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}