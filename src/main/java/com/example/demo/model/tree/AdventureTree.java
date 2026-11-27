package com.example.demo.model.tree;

import com.example.demo.exception.InvalidDecisionException;
import com.example.demo.model.entities.enemies.Goblin;
import com.example.demo.model.entities.enemies.Skeleton;

// Monta a narrativa e acompanha o avanço do jogador pela árvore.
public class AdventureTree {
    private DecisionNode root;
    private DecisionNode currentNode;

    public AdventureTree() {
        root = buildTree();
        currentNode = root;
    }

    public Scenario getCurrentScenario() {
        return currentNode.getScenario();
    }

    public DecisionNode getCurrentNode() {
        return currentNode;
    }

    // Nos dois sentidos, uma escolha inválida mantém o nó atual.
    public Scenario moveLeft() {
        DecisionNode left = currentNode.getLeft();
        if (left == null) {
            throw new InvalidDecisionException("There is no path to the left in this scenario.");
        }
        currentNode = left;
        return getCurrentScenario();
    }

    public Scenario moveRight() {
        DecisionNode right = currentNode.getRight();
        if (right == null) {
            throw new InvalidDecisionException("There is no path to the right in this scenario.");
        }
        currentNode = right;
        return getCurrentScenario();
    }

    // Reconstrói os encontros para que os inimigos voltem com a vida inicial.
    public Scenario reset() {
        root = buildTree();
        currentNode = root;
        return getCurrentScenario();
    }

    public boolean isCurrentLeaf() {
        return currentNode.isLeaf();
    }

    // Cria primeiro as folhas, depois os nós intermediários e depos a raiz.
    private DecisionNode buildTree() {
        DecisionNode goblinLair = new DecisionNode(
                new Scenario("Goblin Lair", "A goblin leaps out from behind the rocks and blocks your way.",
                        new Goblin("Lair Goblin", 20), null, null),
                null, null, null, null);

        DecisionNode safeRetreat = new DecisionNode(
                new Scenario("Safe Retreat", "You retrace your steps through the tunnel and reach the forest trail safely.",
                        null, null, null),
                null, null, null, null);

        DecisionNode skeletonCrypt = new DecisionNode(
                new Scenario("Skeleton Crypt", "At the foot of the stairs, a skeleton rises beside an open tomb.",
                        new Skeleton("Crypt Skeleton", 20), null, null),
                null, null, null, null);

        DecisionNode ruinsExit = new DecisionNode(
                new Scenario("Ruins Exit", "A gap in the wall leads you back into daylight.",
                        null, null, null),
                null, null, null, null);

        DecisionNode goblinCave = new DecisionNode(
                new Scenario("Goblin Cave", "Fresh footprints lead deeper into the cave.",
                        null, null, null),
                "Advance to the lair", "Retreat", goblinLair, safeRetreat);

        DecisionNode ancientRuins = new DecisionNode(
                new Scenario("Ancient Ruins", "Between moss-covered walls, a stairway descends into darkness.",
                        null, null, null),
                "Descend into the crypt", "Look for an exit", skeletonCrypt, ruinsExit);

        return new DecisionNode(
                new Scenario("Dark Forest", "The trail forks, with a cave entrance on one side and ancient ruins on the other.",
                        null, null, null),
                "Enter the cave", "Investigate the ruins", goblinCave, ancientRuins);
    }
}
