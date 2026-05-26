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

    private String type;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Transaction> transactions;
}