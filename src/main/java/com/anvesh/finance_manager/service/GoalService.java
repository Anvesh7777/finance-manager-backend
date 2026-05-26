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

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->

                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    // CREATE GOAL
    public Goal createGoal(
            Goal goal
    ) {

        // VALIDATION
        if (
                goal.getGoalName() == null
                        || goal.getGoalName()
                        .trim()
                        .isEmpty()
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

        if (
                goal.getTargetDate() == null
        ) {

            throw new RuntimeException(
                    "Target date is required"
            );
        }

        // FUTURE DATE CHECK
        if (
                goal.getTargetDate()
                        .isBefore(LocalDate.now())
        ) {

            throw new RuntimeException(
                    "Target date must be future date"
            );
        }

        User currentUser =
                getCurrentUser();

        goal.setUser(currentUser);

        // AUTO START DATE
        if (
                goal.getStartDate() == null
        ) {

            goal.setStartDate(
                    LocalDate.now()
            );
        }

        // DEFAULT SAVED AMOUNT
        if (
                goal.getSavedAmount() == null
        ) {

            goal.setSavedAmount(0.0);
        }

        return goalRepository.save(goal);
    }

    // GET ALL GOALS
    public List<Map<String, Object>> getAllGoals() {

        User currentUser =
                getCurrentUser();

        List<Goal> goals =
                goalRepository
                        .findByUser(currentUser);

        List<Transaction> transactions =
                transactionRepository
                        .findByUserOrderByDateDesc(
                                currentUser
                        );

        List<Map<String, Object>> response =
                new ArrayList<>();

        for (
                Goal goal : goals
        ) {

            double income = 0;

            double expense = 0;

            for (
                    Transaction transaction
                    : transactions
            ) {

                if (
                        transaction.getDate() == null
                ) {

                    continue;
                }

                // ONLY AFTER GOAL START DATE
                if (
                        transaction.getDate()
                                .isAfter(
                                        goal.getStartDate()
                                )
                                ||
                                transaction.getDate()
                                        .isEqual(
                                                goal.getStartDate()
                                        )
                ) {

                    if (
                            transaction.getCategory()
                                    .getType()
                                    .equalsIgnoreCase(
                                            "INCOME"
                                    )
                    ) {

                        income +=
                                transaction.getAmount();

                    } else {

                        expense +=
                                transaction.getAmount();
                    }
                }
            }

            // NET SAVINGS
            double progress =
                    income - expense;

            // PREVENT NEGATIVE
            progress =
                    Math.max(progress, 0);

            // LIMIT %
            double percentage =
                    (progress /
                            goal.getTargetAmount()) * 100;

            percentage =
                    Math.min(percentage, 100);

            // REMAINING
            double remaining =
                    goal.getTargetAmount()
                            - progress;

            remaining =
                    Math.max(remaining, 0);

            Map<String, Object> goalData =
                    new HashMap<>();

            goalData.put(
                    "id",
                    goal.getId()
            );

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
                    percentage
            );

            goalData.put(
                    "remainingAmount",
                    remaining
            );

            response.add(goalData);
        }

        return response;
    }

    // GET GOAL BY ID
    public Map<String, Object> getGoalById(
            Long id
    ) {

        Goal goal =
                goalRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Goal not found"
                                )
                        );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "id",
                goal.getId()
        );

        response.put(
                "goalName",
                goal.getGoalName()
        );

        response.put(
                "targetAmount",
                goal.getTargetAmount()
        );

        response.put(
                "savedAmount",
                goal.getSavedAmount()
        );

        response.put(
                "targetDate",
                goal.getTargetDate()
        );

        response.put(
                "startDate",
                goal.getStartDate()
        );

        return response;
    }
}