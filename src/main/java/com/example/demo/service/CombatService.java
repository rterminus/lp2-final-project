package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.exception.InsufficientHealthException;
import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.entities.enemies.Enemy;

@Service
public class CombatService {

    private final AdventurerService adventurerService;
    private final EnemyService enemyService;

    public CombatService(AdventurerService adventurerService, EnemyService enemyService) {
        this.adventurerService = adventurerService;
        this.enemyService = enemyService;
    }

    public Enemy attackEnemy() {
        Adventurer adventurer = adventurerService.getActiveAdventurer();

        if (adventurer.getLife() <= 0) {
            throw new InsufficientHealthException("Adventurer '" + adventurer.getName() + "' is dead and cannot attack.");
        }

        Enemy enemy = enemyService.getCurrentEnemy();
        if (enemy.getLife() <= 0) {
            throw new IllegalStateException("The enemy '" + enemy.getName() + "' is already dead.");
        }

        return enemyService.receiveDamage(adventurer.getDamage());
    }

    public Adventurer enemyCounterAttack() {
        Enemy enemy = enemyService.getCurrentEnemy();

        if (enemy.getLife() <= 0) {
            throw new IllegalStateException("The enemy '" + enemy.getName() + "' is dead and cannot counter-attack.");
        }

        // Uses polymorphic attack() implemented by Goblin, Skeleton, etc.
        int enemyDamage = enemy.attack();
        return adventurerService.receiveDamage(enemyDamage);
    }

    public String executeTurn() {
        Adventurer adventurer = adventurerService.getActiveAdventurer();
        Enemy enemy = attackEnemy();

        if (enemy.getLife() == 0) {
            return "You defeated " + enemy.getName() + "!";
        }

        enemyCounterAttack();
        if (adventurer.getLife() == 0) {
            return enemy.getName() + " defeated you!";
        }

        return "You dealt " + adventurer.getDamage() + " damage to " + enemy.getName()
                + " (HP: " + enemy.getLife() + ") and took " + enemy.getDamage()
                + " damage in return (your HP: " + adventurer.getLife() + ").";
    }
}
