package com.example.demo.model.tree;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.exception.InvalidDecisionException;
import com.example.demo.model.entities.enemies.Enemy;
import com.example.demo.model.entities.enemies.Goblin;
import com.example.demo.model.entities.enemies.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AdventureTreeTest {
    private AdventureTree tree;

    @BeforeEach
    void setUp() {
        tree = new AdventureTree();
    }

    @Test
    void startsInDarkForest() {
        Scenario scenario = tree.getCurrentScenario();
        assertEquals("Dark Forest", scenario.getTitle());
        assertSame(scenario, tree.getCurrentNode().getScenario());
        assertFalse(tree.isCurrentLeaf());
        assertNull(scenario.getEnemy());
    }

    @Test
    void reachesGoblinLairThroughGoblinCave() {
        Scenario cave = tree.moveLeft();
        assertEquals("Goblin Cave", cave.getTitle());
        assertFalse(tree.isCurrentLeaf());
        assertNull(cave.getEnemy());

        Scenario lair = tree.moveLeft();
        assertEquals("Goblin Lair", lair.getTitle());
        assertTrue(tree.isCurrentLeaf());
        assertInstanceOf(Goblin.class, lair.getEnemy());
        assertEquals("Lair Goblin", lair.getEnemy().getName());
    }

    @Test
    void reachesSafeRetreatThroughGoblinCave() {
        tree.moveLeft();
        Scenario retreat = tree.moveRight();

        assertEquals("Safe Retreat", retreat.getTitle());
        assertTrue(tree.isCurrentLeaf());
        assertNull(retreat.getEnemy());
    }

    @Test
    void reachesSkeletonCryptThroughAncientRuins() {
        Scenario ruins = tree.moveRight();
        assertEquals("Ancient Ruins", ruins.getTitle());
        assertFalse(tree.isCurrentLeaf());
        assertNull(ruins.getEnemy());

        Scenario crypt = tree.moveLeft();
        assertEquals("Skeleton Crypt", crypt.getTitle());
        assertTrue(tree.isCurrentLeaf());
        assertInstanceOf(Skeleton.class, crypt.getEnemy());
        assertEquals("Crypt Skeleton", crypt.getEnemy().getName());
    }

    @Test
    void reachesRuinsExitThroughAncientRuins() {
        tree.moveRight();
        Scenario exit = tree.moveRight();

        assertEquals("Ruins Exit", exit.getTitle());
        assertTrue(tree.isCurrentLeaf());
        assertNull(exit.getEnemy());
    }

    @Test
    void rejectsBothChoicesAtALeafAndKeepsTheCurrentNode() {
        tree.moveLeft();
        tree.moveRight();
        DecisionNode leaf = tree.getCurrentNode();

        assertThrows(InvalidDecisionException.class, tree::moveLeft);
        assertSame(leaf, tree.getCurrentNode());
        assertThrows(InvalidDecisionException.class, tree::moveRight);
        assertSame(leaf, tree.getCurrentNode());
    }

    @Test
    void resetReturnsToDarkForest() {
        tree.moveRight();
        tree.moveRight();

        Scenario initial = tree.reset();

        assertEquals("Dark Forest", initial.getTitle());
        assertSame(initial, tree.getCurrentScenario());
        assertFalse(tree.isCurrentLeaf());
    }

    @Test
    void resetReplacesDefeatedGoblinWithAnEnemyAtFullLife() {
        tree.moveLeft();
        Enemy defeated = tree.moveLeft().getEnemy();
        int initialLife = defeated.getLife();
        defeated.receiveDmg(initialLife);
        assertEquals(0, defeated.getLife());

        tree.reset();
        tree.moveLeft();
        Enemy restored = tree.moveLeft().getEnemy();

        assertNotSame(defeated, restored);
        assertEquals(initialLife, restored.getLife());
    }

    @Test
    void resetReplacesDefeatedSkeletonWithAnEnemyAtFullLife() {
        tree.moveRight();
        Enemy defeated = tree.moveLeft().getEnemy();
        int initialLife = defeated.getLife();
        defeated.receiveDmg(initialLife);
        assertEquals(0, defeated.getLife());

        tree.reset();
        tree.moveRight();
        Enemy restored = tree.moveLeft().getEnemy();

        assertNotSame(defeated, restored);
        assertEquals(initialLife, restored.getLife());
    }

    @Test
    void scenarioAcceptsMissingOptionalAssociations() {
        Scenario scenario = new Scenario("Camp", "A quiet clearing.", null, null, null);

        assertNull(scenario.getEnemy());
        assertNull(scenario.getQuest());
        assertNull(scenario.getReward());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void scenarioRejectsInvalidTitleOrDescription(String invalidText) {
        assertThrows(IllegalArgumentException.class,
                () -> new Scenario(invalidText, "A quiet clearing.", null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> new Scenario("Camp", invalidText, null, null, null));
    }

    @Test
    void decisionNodeRequiresAScenario() {
        assertThrows(IllegalArgumentException.class,
                () -> new DecisionNode(null, null, null, null, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void decisionNodeRequiresAnOptionForEachChild(String invalidOption) {
        DecisionNode child = tree.getCurrentNode().getLeft();
        Scenario scenario = tree.getCurrentScenario();

        assertThrows(IllegalArgumentException.class,
                () -> new DecisionNode(scenario, invalidOption, null, child, null));
        assertThrows(IllegalArgumentException.class,
                () -> new DecisionNode(scenario, null, invalidOption, null, child));
    }

    @Test
    void decisionNodeRejectsOptionsWithoutChildren() {
        Scenario scenario = tree.getCurrentScenario();

        assertThrows(IllegalArgumentException.class,
                () -> new DecisionNode(scenario, "Go left", null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> new DecisionNode(scenario, null, "Go right", null, null));
    }

    @Test
    void decisionNodeWithOnlyOneChildIsNotALeaf() {
        DecisionNode child = tree.getCurrentNode().getLeft();
        Scenario scenario = tree.getCurrentScenario();

        assertFalse(new DecisionNode(scenario, "Go left", null, child, null).isLeaf());
        assertFalse(new DecisionNode(scenario, null, "Go right", null, child).isLeaf());
    }
}
