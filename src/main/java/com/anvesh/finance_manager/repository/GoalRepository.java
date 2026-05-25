package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}