package com.example.hp.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

    private static boolean[] keys, justPressed, cantPressed;
	public static int up, down, left, right, space, R;

	public KeyManager() {
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPressed = new boolean[keys.length];
	}

	public void tick() {
		for (int i = 0; i < keys.length; i++) {
			if (cantPressed[i] && !keys[i]) {
				cantPressed[i] = false;
			} else if (justPressed[i]) {
				cantPressed[i] = true;
				justPressed[i] = false;
			}
			if (!cantPressed[i] && keys[i]) {
				justPressed[i] = true;
			}
		}

		up = KeyEvent.VK_UP;
		down = KeyEvent.VK_DOWN;
		right = KeyEvent.VK_RIGHT;
		left = KeyEvent.VK_LEFT;
        R = KeyEvent.VK_R;
	}

	public static boolean keyJustPressed(int vkF) {
		if (justPressed[vkF]) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
			return;
		}
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
			return;
		}
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}