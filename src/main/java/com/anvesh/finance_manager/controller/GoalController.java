package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.entity.Goal;
import com.anvesh.finance_manager.service.GoalService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // CREATE GOAL
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Goal createGoal(
            @RequestBody Goal goal
    ) {

        // BASIC VALIDATION
        if (
                goal.getGoalName() == null
                        || goal.getGoalName().trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Goal name is required"
            );
        }

        if (
                goal.getTargetAmount() == null
                        || goal.getTargetAmount() <= 0
        ) {

            throw new RuntimeException(
                    "Target amount must be greater than 0"
            );
        }

        return goalService
                .createGoal(goal);
    }

    // GET ALL GOALS
    @GetMapping
    public List<Map<String, Object>> getAllGoals() {

        return goalService
                .getAllGoals();
    }

    // GET GOAL BY ID
    @GetMapping("/{id}")
    public Map<String, Object> getGoalById(
            @PathVariable Long id
    ) {

        return goalService
                .getGoalById(id);
    }
}