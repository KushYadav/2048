package com.example.hp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.example.hp.gfx.Assets;
import com.example.hp.input.KeyManager;
import com.example.hp.utils.Util;

public class Movement {

	private Integer[][] board;
	private Random random = new Random();
	private final int WIDTH = 64;
	private final int HEIGHT = 64;
	private final int TOP_MARGIN = 94;
	private final int MARGIN = 10;

	static int highScore;
	static int currentScore = 0;

	public Movement() {
		board = new Integer[4][4];
		for (Integer[] a : board) {
			Arrays.fill(a, 0);
		}
		String score = Util.loadFileAsString("res/data/score.txt").strip();
		highScore = Util.parseInt(score);
		addNumber();
	}

	public void tick() {
		if (KeyManager.keyJustPressed(KeyManager.up)) {
			System.err.println("UP");
			if (pushUp()) {
				addNumber();
			}
			display();
		} else if (KeyManager.keyJustPressed(KeyManager.down)) {
			System.out.println("DOWN");
			if (pushDown()) {
				addNumber();
			}
			display();
		} else if (KeyManager.keyJustPressed(KeyManager.right)) {
			System.out.println("RIGHT");
			if (pushRight()) {
				addNumber();
			}
			display();
		} else if (KeyManager.keyJustPressed(KeyManager.left)) {
			System.out.println("LEFT");
			if (pushLeft()) {
				addNumber();
			}
			display();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int num = board[i][j];
				if (num > currentScore) {
					currentScore = num;
				}
				if (currentScore > highScore) {
					highScore = currentScore;
					Util.writeStringInFile(String.valueOf(highScore), "res/data/score.txt");
				}

				// g.setColor(Color.decode("#F7DB69"));
				g.setColor(Color.WHITE);
				g.setFont(new Font("Comic Sans MS", 35, 25));
				g.drawString("Current Score: " + currentScore, 10, 35);
				g.drawString("High Score: " + highScore, 10, 75);
				g.drawImage(Assets.map.get(num), WIDTH * j + MARGIN * (j + 1),
						HEIGHT * i + MARGIN * (i + 1) + TOP_MARGIN, WIDTH, HEIGHT, null);
			}
		}
		if (gameOver()) {
			// g.setColor(Color.decode("#34485E"));
			// g.setColor(new Color(52,72,94,200));
			g.setColor(new Color(200, 200, 200, 200));
			g.fillRect(0, 0, 306, 400);
			g.setColor(Color.RED);
			g.setFont(new Font("Comic Sans MS", 40, 50));
			g.drawString("Game Over", 20, 180);
			g.setFont(new Font("Comic Sans MS", 40, 15));
			g.drawString("Press R to play again!", 80, 210);
			if (KeyManager.keyJustPressed(KeyManager.R)) {
				for (Integer[] a : board) {
					Arrays.fill(a, 0);
				}
				currentScore = 0;
				System.out.println("Restart");
				addNumber();
			}
			g.setColor(Color.red);
		}
	}

	public void addNumber() {
		ArrayList<Integer[]> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0) {
					Integer[] c = new Integer[] { i, j };
					list.add(c);
				}
			}
		}

		Integer choices = random.nextInt(list.size());
		Integer numberChooser = random.nextInt(10);

		Integer newNumber = 2;
		if (numberChooser == 0) {
			newNumber = 4;

		}

		Integer[] coordinates = list.get(choices);
		board[coordinates[0]][coordinates[1]] = newNumber;
	}

	public boolean pushUp() {
		boolean isCombined = false;
		boolean isGap = false;
		int ln = -1;
		int count = -1;
		for (int j = 0; j < 4; j++) {
			count = -1;
			ln = -1;
			ArrayList<Integer> ns = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				if (board[i][j] != 0) {
					ln = i;
					count++;
					ns.add(board[i][j]);
					board[i][j] = 0;
				}
			}

			for (int i = 1; i < ns.size(); i++) {
				if (ns.get(i).equals(ns.get(i - 1))) {
					isCombined = true;
					ns.set(i, 0);
					currentScore += ns.get(i - 1) * 2;
					ns.set(i - 1, ns.get(i - 1) * 2);
					i++;
				}
			}

			int k = 0;
			for (int i = 0; i < ns.size(); i++) {
				if (ns.get(i) != 0) {
					board[k++][j] = ns.get(i);
				}
			}

			isGap |= ln != count;
		}
		return isGap || isCombined;
	}

	public boolean pushDown() {
		boolean isCombined = false;
		boolean isGap = false;
		int ln = 4;
		int count = -1;
		for (int j = 0; j < 4; j++) {
			ln = 4;
			count = -1;
			ArrayList<Integer> ns = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				if (board[i][j] != 0) {
					if (ln == 4) {
						ln = i;
					}
					count++;
					ns.add(board[i][j]);
					board[i][j] = 0;
				}
			}

			for (int i = ns.size() - 2; i >= 0; i--) {
				if (ns.get(i).equals(ns.get(i + 1))) {
					isCombined = true;
					ns.set(i, 0);
					currentScore += ns.get(i + 1) * 2;
					ns.set(i + 1, ns.get(i + 1) * 2);
					i--;
				}
			}

			int k = 3;
			for (int i = ns.size() - 1; i >= 0; i--) {
				if (ns.get(i) != 0) {
					board[k--][j] = ns.get(i);
				}
			}
			ln = 3 - ln;
			isGap |= ln != count;
		}
		return isGap || isCombined;
	}

	public boolean pushLeft() {
		boolean isCombined = false;
		boolean isGap = false;
		int ln = -1;
		int count = -1;
		for (int i = 0; i < 4; i++) {
			ln = -1;
			count = -1;
			ArrayList<Integer> ns = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				if (board[i][j] != 0) {
					ln = j;
					count++;
					ns.add(board[i][j]);
					board[i][j] = 0;
				}
			}

			for (int j = 1; j < ns.size(); j++) {
				if (ns.get(j).equals(ns.get(j - 1))) {
					isCombined = true;
					ns.set(j, 0);
					currentScore += ns.get(j - 1) * 2;
					ns.set(j - 1, ns.get(j - 1) * 2);
					j++;
				}
			}

			int k = 0;
			for (int j = 0; j < ns.size(); j++) {
				if (ns.get(j) != 0) {
					board[i][k++] = ns.get(j);
				}
			}
			isGap |= ln != count;
		}
		return isGap || isCombined;
	}

	public boolean pushRight() {
		boolean isCombined = false;
		boolean isGap = false;
		int ln = 4;
		int count = -1;
		for (int i = 0; i < 4; i++) {
			ln = 4;
			count = -1;
			ArrayList<Integer> ns = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				if (board[i][j] != 0) {
					if (ln == 4) {
						ln = j;
					}
					count++;
					ns.add(board[i][j]);
					board[i][j] = 0;
				}
			}

			for (int j = ns.size() - 2; j >= 0; j--) {
				if (ns.get(j).equals(ns.get(j + 1))) {
					isCombined = true;
					ns.set(j, 0);
					currentScore += ns.get(j + 1) * 2;
					ns.set(j + 1, ns.get(j + 1) * 2);
					j--;
				}
			}

			int k = 3;
			for (int j = ns.size() - 1; j >= 0; j--) {
				if (ns.get(j) != 0) {
					board[i][k--] = ns.get(j);
				}
			}
			ln = 3 - ln;
			isGap |= ln != count;
		}
		return isGap || isCombined;
	}

	public boolean gameOver() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (board[i][j] == board[i][j - 1])
					return false;
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 1; i < 4; i++) {
				if (board[i][j] == board[i - 1][j])
					return false;
			}
		}

		return true;
	}

	public void display() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

}
