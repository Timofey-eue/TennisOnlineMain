package com.company.model;

import java.awt.*;

public class GameModel {

    public static final int defaultY1 = 150, defaultY2 = 150, defaultX1 = 0, defaultX2 = 590;

    public static final int playerSpeed = 20, playerWidth = 10, playerHeight = 100;

    public static final int defaultBallX = 294, defaultBallY = 194,
            ballSize = 17, defaultBallSpeedX = 30, defaultBallSpeedY = 30;

    private int score1, score2, maxScore;

    private int player1Y, player2Y;

    private int ballX, ballY, ballSpeedX, ballSpeedY;

    public GameModel() {
        score1 = 0;
        score2 = 0;
        maxScore = 0;
        setDefault();
    }

    public void setDefault() {
        player1Y = defaultY1;
        player2Y = defaultY2;
        ballX = defaultBallX;
        ballY = defaultBallY;
        ballSpeedX = defaultBallSpeedX;
        ballSpeedY = defaultBallSpeedY;
    }

    public boolean checkBallCollision() {
        return new Rectangle(ballX, ballY, ballSize, ballSize).
                intersects(new Rectangle(0, player1Y, playerWidth, playerHeight));
    }

    public boolean checkBallRebound() {
        return ballY <= 0 || ballY > 385;
    }

    public boolean checkBallIsOut() {
        return ballX < -20;
    }

    public int checkEndGame() {
        if (score1 == maxScore)
            return 1;
        else if (score2 == maxScore)
            return 2;
        else return 0;
    }

    public void setPlayer1Y(int y1) {
        player1Y = y1;
    }
    public void setPlayer2Y(int y2) {
        player2Y = y2;
    }
    public int getPlayer1Y() {
        return player1Y;
    }
    public int getPlayer2Y() {
        return player2Y;
    }
    public int getPlayer1X() {
        return defaultX1;
    }
    public int getPlayer2X() {
        return defaultX2;
    }
    public void incrPlayer1Y() {
        player1Y += playerSpeed;
    }
    public void decrPlayer1Y() {
        player1Y -= playerSpeed;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }
    public int getScore1() {
        return score1;
    }
    public int getScore2() {
        return score2;
    }
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
    public int getMaxScore() {
        return maxScore;
    }
    public void incrScore2() {
        score2++;
    }

    public void setBallX(int x) {
        ballX = x;
    }
    public void setBallY(int y) {
        ballY = y;
    }
    public int getBallX() {
        return ballX;
    }
    public int getBallY() {
        return ballY;
    }
    public void setBallSpeedX(int speedX) {
        ballSpeedX = speedX;
    }
    public void changeBallSpeedX() {
        ballSpeedX *= -1;
    }
    public void changeBallSpeedY() {
        ballSpeedY *= -1;
    }
    public void moveBallX() {
        ballX += ballSpeedX;
    }
    public void moveBallY() {
        ballY += ballSpeedY;
    }
}