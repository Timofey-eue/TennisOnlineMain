package com.company.view.panels.game;

import com.company.model.GameBot;
import com.company.view.panels.BasePanel;
import com.company.view.panels.menu.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameBotPanel extends BasePanel implements ActionListener, KeyListener {

	private BufferedImage court;
	private int playerY, botY;
	private final GameBot bot;
	private int ballX, ballY, ballSpeedX = 3, ballSpeedY = 3;
	private int score1 = 0, score2 = 0;
	private final int maxScore = 10;
	private final Timer FPS;

	public GameBotPanel(int difficulty) {
		addKeyListener(this);
		FPS = new Timer(10, this);
		setFocusable(true);
		bot = new GameBot(difficulty);
		setDefault();
	}

	@Override
	public void paint(Graphics g) {

		try {
			court = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/bg.png")));
		}
		catch (IOException e) { e.printStackTrace();}
		g.drawImage(court, 0,0, WIDTH, HEIGHT, this);

		g.setColor(new Color(0, 0, 255));
		int playerX = 0;
		g.fillRect(playerX, playerY, 10, 100);
		g.setColor(new Color(255, 0, 0));
		g.fillRect(GameBot.defaultBotX, botY, GameBot.width, GameBot.height);

		g.setColor(new Color(0, 0, 0));
		int ballSize = 17;
		g.fillOval(ballX - 2, ballY - 2, ballSize, ballSize);
		g.setColor(new Color(255, 250, 205));
		g.fillOval(ballX, ballY, ballSize - 4, ballSize - 4);

		Font f = new Font("Arial", Font.BOLD, 25);
		g.setFont(f);
		g.drawString(String.valueOf(score1), 260, 30);
		g.drawString(String.valueOf(score2), 325, 30);
		Font f1 = new Font("Arial", Font.BOLD, 15);
		g.setFont(f1);
		g.drawString("Max Score: " + maxScore, 425, 30);
	}

	private void setDefault() {
		ballX = 294;
		ballY = 194;
		ballSpeedX = 3;
		playerY = 150;
		botY = GameBot.defaultBotY;
		bot.setBotY(GameBot.defaultBotY);
	}

	private int checkEndGame() {
		if (score1 == maxScore)
			return 1;
		else if (score2 == maxScore)
			return 2;
		else return 0;
	}

	private void endGame() {
		FPS.stop();
		window.getContentPane().removeAll();
		MenuPanel menu = new MenuPanel();
		window.add(menu);
		window.pack();
		menu.addFrame(window);
	}

	private void botMove() {
		if (ballY - 50 <= botY && botY > 4 ) {
			botY = bot.botMoveDown();
		}
		else if (ballY - 50 > botY && botY < 296 ) {
			botY = bot.botMoveUp();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		ballX += ballSpeedX;
		ballY += ballSpeedY;
		if (ballY <= 0 || ballY > 385) {
			ballSpeedY *= -1;
		}
		if (ballX >= 330 && ballSpeedX > 0) {
			botMove();
		}
		if (new Rectangle(ballX, ballY, 14, 14).intersects(new Rectangle(GameBot.defaultBotX, botY, 10, 100))
			|| new Rectangle(ballX, ballY, 14, 14).intersects(new Rectangle(0, playerY, 10, 100)))
			ballSpeedX *= -1;
		if (ballX < 0) {
			score2++;
			setDefault();
		}
		if (ballX > 600) {
			score1++;
			setDefault();
			ballSpeedX *= -1;
		}
		if (checkEndGame() == 1) {
			callInformationWindow("Game over. You win!");
			endGame();
		} else if (checkEndGame() == 2) {
			callInformationWindow("Game over. You lose!");
			endGame();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN && playerY <= 292) {
			playerY += 15;
		} else if (e.getKeyCode() == KeyEvent.VK_UP && playerY > 5) {
			playerY -= 15;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			FPS.start();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			endGame();
		}
	}

	private static void callInformationWindow(String message) {
		JOptionPane.showMessageDialog(null, message, "Attention!", JOptionPane.ERROR_MESSAGE);
	}
}