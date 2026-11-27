package com.example.demo.model.tree;

import com.example.demo.model.entities.enemies.Enemy;
import com.example.demo.model.quests.Quest;
import com.example.demo.model.quests.Reward;

public class Scenario {
    private final String title;
    private final String description;
    private final Enemy enemy;
    private final Quest quest;
    private final Reward reward;

    public Scenario(String title, String description, Enemy enemy, Quest quest, Reward reward) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Scenario title cannot be null or blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Scenario description cannot be null or blank.");
        }

        this.title = title;
        this.description = description;
        this.enemy = enemy;
        this.quest = quest;
        this.reward = reward;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Quest getQuest() {
        return quest;
    }

    public Reward getReward() {
        return reward;
    }
}
