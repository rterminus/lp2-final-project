package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.entities.enemies.Enemy;
import com.example.demo.model.tree.AdventureTree;
import com.example.demo.model.tree.DecisionNode;
import com.example.demo.model.tree.Scenario;

@Service 
public class AdventureService {

    public record AdventureState(String title, String description,
                                 String leftOption, String rightOption,
                                 Enemy enemy, boolean finished) {}

    private final AdventureTree tree = new AdventureTree();
    private final EnemyService enemyService;

    public AdventureService(EnemyService enemyService) {
        this.enemyService = enemyService;
        enemyService.setCurrentEnemy(tree.getCurrentScenario().getEnemy());
    }

    public AdventureState getCurrent() { return toState(); }

    public AdventureState chooseLeft() {
        return updateEncounter(tree.moveLeft());
    }

    public AdventureState chooseRight() {
        return updateEncounter(tree.moveRight());
    }

    public AdventureState reset() {
        return updateEncounter(tree.reset());
    }

    private AdventureState updateEncounter(Scenario scenario) {
        enemyService.setCurrentEnemy(scenario.getEnemy());
        return toState();
    }

    private AdventureState toState() {
        Scenario s = tree.getCurrentScenario();
        DecisionNode node = tree.getCurrentNode();
        return new AdventureState(s.getTitle(), s.getDescription(),
                node.getLeftOption(), node.getRightOption(),
                s.getEnemy(), tree.isCurrentLeaf());
    }
}
