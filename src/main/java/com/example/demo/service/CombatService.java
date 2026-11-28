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
        Enemy enemy = getAttackTarget(adventurer);
        adventurer.attack(enemy); // polimórfico: Warrior/Mage causam dano diferente
        return enemy;
    }

    private Enemy getAttackTarget(Adventurer adventurer) {
        if (adventurer.getLife() <= 0) {
            throw new InsufficientHealthException("Adventurer '" + adventurer.getName() + "' is dead and cannot attack.");
        }

        Enemy enemy = enemyService.getCurrentEnemy();
        if (enemy.getLife() <= 0) {
            throw new IllegalStateException("The enemy '" + enemy.getName() + "' is already dead.");
        }

        return enemy;
    }

    public Adventurer enemyCounterAttack() {
        Enemy enemy = enemyService.getCurrentEnemy();
        Adventurer adventurer = adventurerService.getActiveAdventurer();

        if (enemy.getLife() <= 0) {
            throw new IllegalStateException("The enemy '" + enemy.getName() + "' is dead and cannot counter-attack.");
        }

        enemy.attack(adventurer);
        return adventurer;
    }

    public String executeTurn() {
        Adventurer adventurer = adventurerService.getActiveAdventurer();
        Enemy enemy = getAttackTarget(adventurer);
        int enemyLifeBefore = enemy.getLife();
        adventurer.attack(enemy);
        int damageDealt = enemyLifeBefore - enemy.getLife();

        if (enemy.getLife() == 0) {
            return "You defeated " + enemy.getName() + "!";
        }

        int adventurerLifeBefore = adventurer.getLife();
        enemyCounterAttack();
        int damageTaken = adventurerLifeBefore - adventurer.getLife();
        if (adventurer.getLife() == 0) {
            return enemy.getName() + " defeated you!";
        }

        return "You dealt " + damageDealt + " damage to " + enemy.getName()
                + " (HP: " + enemy.getLife() + ") and took " + damageTaken
                + " damage in return (your HP: " + adventurer.getLife() + ").";
    }
}
