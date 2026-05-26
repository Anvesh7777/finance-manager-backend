package com.anvesh.finance_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "category")
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // INCOME / EXPENSE
    private String type;

    // DEFAULT CATEGORY FLAG
    private boolean defaultCategory;

    // CATEGORY OWNER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"transactions"})
    private User user;

    // TRANSACTIONS
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Transaction> transactions;
}