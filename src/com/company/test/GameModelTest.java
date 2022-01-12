package com.company.test;

import com.company.model.GameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void checkBallCollision() {
        GameModel gm = new GameModel();
        gm.setBallX(0);
        gm.setBallY(GameModel.defaultY1);
        boolean expected = true;
        assertEquals(gm.checkBallCollision(), expected);
    }

    @Test
    void checkBallRebound() {
        GameModel gm = new GameModel();
        gm.setBallX(50);
        gm.setBallY(0);
        boolean expected = true;
        assertEquals(gm.checkBallRebound(), expected);
    }

    @Test
    void checkBallIsOut() {
        GameModel gm = new GameModel();
        gm.setBallX(-21);
        boolean expected = true;
        assertEquals(gm.checkBallIsOut(), expected);
    }

    @Test
    void checkEndGame() {
        GameModel gm = new GameModel();
        gm.setMaxScore(5);
        gm.setScore1(5);
        int expected = 1;
        assertEquals(gm.checkEndGame(), expected);
    }
}