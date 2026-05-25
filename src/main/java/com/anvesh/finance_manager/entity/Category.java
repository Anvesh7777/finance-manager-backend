package com.anvesh.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    // DEFAULT CATEGORY OR CUSTOM
    private boolean isDefaultCategory = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}