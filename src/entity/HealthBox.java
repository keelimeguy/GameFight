package entity;

import java.util.ArrayList;

import entity.mob.Player;

public class HealthBox extends HitBox {

	private Player player;

	public HealthBox(int x, int y, int width, int height, boolean harmful, int damage, Player player) {
		super(x, y, width, height, harmful, damage);
		this.player = player;
	}

	public HealthBox(int x, int y, int width, int height, Player player) {
		super(x, y, width, height);
		this.player = player;
	}

	public HealthBox(int x, int y, int width, int height, boolean harmful, int damage, ArrayList<Entity> noHarm, Player player) {
		super(x, y, width, height, harmful, damage, noHarm);
		this.player = player;
	}

	public void hurt(int damage) {
		player.takeDamage(damage);
	}
}
