package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.exception.CharacterNotFoundException;
import com.example.demo.model.entities.enemies.Enemy;
import com.example.demo.model.entities.enemies.Goblin;

@Service
public class EnemyService {

    private Enemy currentEnemy = new Goblin("Forest Goblin", 20);

    public Enemy receiveDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative.");
        }

        Enemy enemy = getCurrentEnemy();
        enemy.receiveDmg(damage);
        return enemy;
    }

    public Enemy getCurrentEnemy() {
        if (currentEnemy == null) {
            throw new CharacterNotFoundException("No active enemy in the current scenario.");
        }
        return this.currentEnemy;
    }

    public void setCurrentEnemy(Enemy enemy) {
        this.currentEnemy = enemy;
    }
}
