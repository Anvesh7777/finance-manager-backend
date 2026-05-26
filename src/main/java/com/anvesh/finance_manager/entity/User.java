package com.anvesh.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @Column(
            nullable = false
    )
    private String password;

    // USER TRANSACTIONS
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Transaction> transactions =
            new ArrayList<>();
}