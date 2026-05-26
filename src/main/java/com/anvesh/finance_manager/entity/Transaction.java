package com.anvesh.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private Double amount;

    @Column(
            nullable = false
    )
    private LocalDate date;

    @Column(
            nullable = false
    )
    private String description;

    // CATEGORY
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    @JsonIgnoreProperties({
            "transactions",
            "user"
    })
    private Category category;

    // USER
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id"
    )
    @JsonIgnoreProperties({
            "transactions",
            "password"
    })
    private User user;
}