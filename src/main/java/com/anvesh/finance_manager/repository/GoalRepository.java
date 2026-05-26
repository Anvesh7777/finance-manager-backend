package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Goal;
import com.anvesh.finance_manager.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository
        extends JpaRepository<Goal, Long> {

    // USER GOALS
    List<Goal> findByUser(
            User user
    );

    // SORTED GOALS
    List<Goal>
    findByUserOrderByTargetDateAsc(
            User user
    );
}