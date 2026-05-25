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

        return goalService
                .createGoal(goal);
    }

    // GET GOALS
    @GetMapping
    public List<Map<String, Object>> getAllGoals() {

        return goalService
                .getAllGoals();
    }
}