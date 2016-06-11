package entity;

import graphics.Screen;

import java.awt.Rectangle;
import java.util.ArrayList;

public class HitBox extends Entity {

	public static HitBox nullBox = new HitBox(0, 0, 0, 0, false, 0, null);

	private ArrayList<Entity> noHarm;
	public boolean harmful = false;
	public int damage = 0;

	public HitBox(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		noHarm = new ArrayList<Entity>();
	}

	public HitBox(int x, int y, int width, int height, boolean harmful, int damage) {
		bounds = new Rectangle(x, y, width, height);
		this.harmful = harmful;
		this.damage = damage;
		noHarm = new ArrayList<Entity>();
	}

	public HitBox(int x, int y, int width, int height, boolean harmful, int damage, ArrayList<Entity> noHarm) {
		bounds = new Rectangle(x, y, width, height);
		this.harmful = harmful;
		this.damage = damage;
		this.noHarm = noHarm;
	}

	public boolean contains(Entity e) {
		boolean ret = false;
		if (!noHarm.contains(e)) {
			ret = bounds.intersects(e.bounds);
			if (ret && harmful) {
				e.hurt(damage);
			}
		}
		return ret;
	}
	
	public void render(Screen screen) {
		if (!harmful)
			screen.renderBox(bounds, 0x77ff0000);
		else
			screen.renderBox(bounds, 0x770000ff);
	}

	public void update() {
		if (level != null) level.collidesWith(this);
	}

}
