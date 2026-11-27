package com.example.demo.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.demo.exception.InsufficientHealthException;
import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.entities.Combatant;
import com.example.demo.model.entities.Mage;
import com.example.demo.model.entities.Warrior;
import com.example.demo.model.entities.enemies.Enemy;
import com.example.demo.model.entities.enemies.Goblin;
import com.example.demo.model.entities.enemies.Skeleton;
import com.example.demo.service.AdventurerService;
import com.example.demo.service.CombatService;
import com.example.demo.service.EnemyService;

class CombatTest {

    @Test
    void attackAppliesDamageToTarget() {
        Adventurer hero = new Adventurer("Hero", 0);
        Goblin goblin = new Goblin("Goblin", 0);

        hero.attack(goblin);

        assertEquals(90, goblin.getLife()); // 100 - 10
    }

    @Test
    void warriorDealsMoreDamageThanBaseAdventurer() {
        Combatant warrior = new Warrior("Conan", 0);
        Goblin goblin = new Goblin("Goblin", 0);

        warrior.attack(goblin);

        assertEquals(85, goblin.getLife()); // 100 - 15
    }

    @Test
    void mageUsesManaAndFallsBackToWeakAttack() {
        Mage mage = new Mage("Merlin", 0);

        for (int i = 0; i < 5; i++) { // 5 x 10 de mana = 50
            Goblin target = new Goblin("Goblin", 0);
            mage.attack(target);
            assertEquals(75, target.getLife()); // 100 - 25
        }
        assertEquals(0, mage.getMana());

        Goblin weak = new Goblin("Goblin", 0);
        mage.attack(weak);
        assertEquals(95, weak.getLife()); // sem mana: 100 - 5
    }

    @Test
    void enemiesAttackPolymorphically() {
        Enemy[] enemies = { new Goblin("Goblin", 0), new Skeleton("Skeleton", 0) };

        for (Enemy enemy : enemies) {
            Adventurer victim = new Adventurer("Victim", 0);
            enemy.attack(victim);
            assertEquals(90, victim.getLife());
        }
    }

    @Test
    void cannotAttackAnEnemyThatIsAlreadyDead() {
        AdventurerService adventurerService = new AdventurerService();
        EnemyService enemyService = new EnemyService();
        Goblin dead = new Goblin("Dead Goblin", 0);
        dead.receiveDmg(100);
        enemyService.setCurrentEnemy(dead);
        CombatService combat = new CombatService(adventurerService, enemyService);

        assertThrows(IllegalStateException.class, combat::attackEnemy);
    }

    @Test
    void deadAdventurerCannotAttack() {
        AdventurerService adventurerService = new AdventurerService();
        adventurerService.getActiveAdventurer().receiveDmg(100);
        CombatService combat = new CombatService(adventurerService, new EnemyService());

        assertThrows(InsufficientHealthException.class, combat::attackEnemy);
    }
}