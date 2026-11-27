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

    public AdventureState getCurrent() { return toState(); }

    public AdventureState chooseLeft()  { tree.moveLeft();  return toState(); }

    public AdventureState chooseRight() { tree.moveRight(); return toState(); }

    public AdventureState reset()       { tree.reset();     return toState(); }

    private AdventureState toState() {
        Scenario s = tree.getCurrentScenario();
        DecisionNode node = tree.getCurrentNode();
        return new AdventureState(s.getTitle(), s.getDescription(),
                node.getLeftOption(), node.getRightOption(),
                s.getEnemy(), tree.isCurrentLeaf());
    }
}