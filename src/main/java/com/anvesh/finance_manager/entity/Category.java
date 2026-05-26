package com.anvesh.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Category {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    // INCOME / EXPENSE
    @Column(
            nullable = false
    )
    private String type;

    // CATEGORY OWNER
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
            "transactions"
    })
    private User user;

    // TRANSACTIONS
    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Transaction> transactions =
            new ArrayList<>();
}