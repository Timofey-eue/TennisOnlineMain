package com.company.model;

public class GameBot {
    public static int defaultBotX = 590, defaultBotY = 150, width = 10, height = 100;
    private int botY;
    private final int speed1, speed2, speed3, speed4, speed5, maxSpeed, p1, p2;

    public GameBot(int difficulty) {
        if (difficulty == 1) {
            p1 = 37;
            p2 = 41;
            speed1 = 2;
            speed2 = 2;
            speed3 = 0;
            speed4 = -1;
            speed5 = -2;
            maxSpeed = 3;
        } else if (difficulty == 2) {
            p1 = 41;
            p2 = 47;
            speed1 = 2;
            speed2 = 3;
            speed3 = 1;
            speed4 = -1;
            speed5 = -2;
            maxSpeed = 4;
        } else {
            p1 = 71;
            p2 = 101;
            speed1 = 5;
            speed2 = 5;
            speed3 = 5;
            speed4 = 5;
            speed5 = 5;
            maxSpeed = 5;
        }
    }

    public int botMoveDown() {
        int rand = (int) (Math.random() * 10000);
        if (rand % 2 == 0 || rand % 7 == 0)
            botY -= speed1;
        else if (rand % 5 == 0 || rand % 17 == 0)
            botY -= speed2;
        else if (rand % 3 == 0 || rand % 13 == 0)
            botY -= speed3;
        else if (rand % 11 == 0 || rand % 19 == 0)
            botY -= speed4;
        else if (rand % 23 == 0)
            botY -= speed5;
        else if (rand % 29 == 0 || rand % 31 == 0 || rand % 47 == 0 || rand % p1 == 0 || rand % p2 == 0)
            botY -= 0;
        else botY -= maxSpeed;
        return botY;
    }

    public int botMoveUp() {
        int rand = (int) (Math.random() * 10000);
        if (rand % 2 == 0 || rand % 7 == 0)
            botY += speed1;
        else if (rand % 5 == 0 || rand % 17 == 0)
            botY += speed2;
        else if (rand % 3 == 0 || rand % 13 == 0)
            botY += speed3;
        else if (rand % 11 == 0 || rand % 19 == 0)
            botY += speed4;
        else if (rand % 23 == 0)
            botY += speed5;
        else if (rand % 29 == 0 || rand % 31 == 0 || rand % 47 == 0 || rand % p1 == 0 || rand % p2 == 0)
            botY += 0;
        else botY += maxSpeed;
        return botY;
    }
    public void setBotY(int y) {
        botY = y;
    }
}