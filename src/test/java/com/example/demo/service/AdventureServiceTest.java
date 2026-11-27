package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.exception.InvalidDecisionException;
import com.example.demo.service.AdventureService.AdventureState;

class AdventureServiceTest {

    private AdventureService service;

    @BeforeEach
    void setUp() {
        service = new AdventureService();
    }

    @Test
    void startsInDarkForest() {
        AdventureState state = service.getCurrent();
        assertEquals("Dark Forest", state.title());
        assertFalse(state.finished());
    }

    @Test
    void leftThenLeftReachesGoblinLair() {
        service.chooseLeft();
        AdventureState state = service.chooseLeft();

        assertEquals("Goblin Lair", state.title());
        assertNotNull(state.enemy());
        assertTrue(state.finished());
    }

    @Test
    void leftThenRightReachesSafeRetreat() {
        service.chooseLeft();
        AdventureState state = service.chooseRight();

        assertEquals("Safe Retreat", state.title());
        assertNull(state.enemy());
        assertTrue(state.finished());
    }

    @Test
    void rightThenLeftReachesSkeletonCrypt() {
        service.chooseRight();
        AdventureState state = service.chooseLeft();

        assertEquals("Skeleton Crypt", state.title());
        assertNotNull(state.enemy());
    }

    @Test
    void rightThenRightReachesRuinsExit() {
        service.chooseRight();
        AdventureState state = service.chooseRight();

        assertEquals("Ruins Exit", state.title());
        assertTrue(state.finished());
    }

    @Test
    void cannotMoveFromALeaf() {
        service.chooseLeft();
        service.chooseRight(); // Safe Retreat (folha)

        assertThrows(InvalidDecisionException.class, service::chooseLeft);
        assertThrows(InvalidDecisionException.class, service::chooseRight);
    }

    @Test
    void resetReturnsToDarkForest() {
        service.chooseRight();
        service.chooseRight();

        assertEquals("Dark Forest", service.reset().title());
    }
}