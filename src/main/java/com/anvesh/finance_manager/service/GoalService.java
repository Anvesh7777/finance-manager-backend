package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.entity.Goal;
import com.anvesh.finance_manager.entity.Transaction;
import com.anvesh.finance_manager.entity.User;

import com.anvesh.finance_manager.repository.GoalRepository;
import com.anvesh.finance_manager.repository.TransactionRepository;
import com.anvesh.finance_manager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // GET CURRENT USER
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));
    }

    // CREATE GOAL
    public Goal createGoal(Goal goal) {

        if (goal.getTargetDate()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Target date must be future date"
            );
        }

        User currentUser = getCurrentUser();

        goal.setUser(currentUser);

        if (goal.getStartDate() == null) {

            goal.setStartDate(LocalDate.now());
        }

        return goalRepository.save(goal);
    }

    // GET ALL GOALS
    public List<Map<String, Object>> getAllGoals() {

        User currentUser = getCurrentUser();

        List<Goal> goals =
                goalRepository.findByUser(currentUser);

        List<Transaction> transactions =
                transactionRepository.findByUser(currentUser);

        List<Map<String, Object>> response =
                new ArrayList<>();

        for (Goal goal : goals) {

            double income = 0;
            double expense = 0;

            for (Transaction transaction : transactions) {

                if (transaction.getDate()
                        .isAfter(goal.getStartDate())
                        ||
                        transaction.getDate()
                                .isEqual(goal.getStartDate())) {

                    if (transaction.getCategory()
                            .getType()
                            .equalsIgnoreCase("INCOME")) {

                        income += transaction.getAmount();

                    } else {

                        expense += transaction.getAmount();
                    }
                }
            }

            double progress = income - expense;

            double percentage =
                    (progress / goal.getTargetAmount()) * 100;

            double remaining =
                    goal.getTargetAmount() - progress;

            Map<String, Object> goalData =
                    new HashMap<>();

            goalData.put("id", goal.getId());

            goalData.put(
                    "goalName",
                    goal.getGoalName()
            );

            goalData.put(
                    "targetAmount",
                    goal.getTargetAmount()
            );

            goalData.put(
                    "targetDate",
                    goal.getTargetDate()
            );

            goalData.put(
                    "startDate",
                    goal.getStartDate()
            );

            goalData.put(
                    "currentProgress",
                    progress
            );

            goalData.put(
                    "progressPercentage",
                    Math.max(0, percentage)
            );

            goalData.put(
                    "remainingAmount",
                    Math.max(0, remaining)
            );

            response.add(goalData);
        }

        return response;
    }
}