package entity;

import graphics.Screen;

import java.awt.Rectangle;
import java.util.Random;

import level.Level;

public abstract class Entity {

	public int x, y;
	private boolean removed = false;
	protected Level level;
	public Rectangle bounds;
	protected final Random random = new Random();

	public Level getLevel() {
		return level;
	}

	public void update() {
	}

	public void render(Screen screen) {
	}

	public void hurt(int damage) {
	}

	/**
	 * Removes the entity from the level
	 */
	public void remove() {
		removed = true;
	}

	/**
	 * Determines if the entity is removed
	 * @return
	 * True if the entity is removed, false otherwise
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * Initiate the current level for the entity
	 * @param level : The level where the entity will be present
	 * 
	 */
	public void init(Level level) {
		this.level = level;
	}
}
