package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[600];
	public boolean up, down, left, right, space, k, w, s, a, d, period, enter, m, zero;

	/**
	 * Sets a flag to determine what action should occur given associated key presses
	 */
	public void update() {
		up = keys[KeyEvent.VK_UP];
		w = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN];
		s = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT];
		a = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT];
		d = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		period = keys[KeyEvent.VK_PERIOD] || keys[KeyEvent.VK_DECIMAL];
		k = keys[KeyEvent.VK_K];
		enter = keys[KeyEvent.VK_ENTER];
		zero = keys[KeyEvent.VK_NUMPAD0];
		m = keys[KeyEvent.VK_M];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}
