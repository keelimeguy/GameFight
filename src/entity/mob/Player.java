package entity.mob;

import entity.Entity;
import entity.HealthBox;
import entity.HitBox;
import entity.projectile.Projectile;
import graphics.PlayerSprite;
import graphics.Screen;
import graphics.Sprite;
import input.Keyboard;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private Sprite[] sprites;
	private HitBox attackBox = HitBox.nullBox;
	private int invanim = 0, anim = 0, curFrame = 0, control = 1;
	public int health = 500;
	private boolean isFlipped = false;
	private boolean walking = false, spaceFree = true, punching = false, kicking = false, invincible = false, dodging = false;

	private int fireRate = 0;
	Projectile p;

	/**
	 * Creates a player at a default location
	 * @param input : The Keyboard controller for this player
	 */
	public Player(Keyboard input) {
		this.input = input;
		sprites = PlayerSprite.playerReady;
		sprite = sprites[0];
		hitBox = new HealthBox(0, 0, sprite.SIZE_X, sprite.SIZE_Y, this);
		attackBox.init(level);
		hitBox.init(level);
		bounds = new Rectangle(0, 0, sprite.SIZE_X, sprite.SIZE_Y);
		fireRate = 0;//FireballProjectile.FIRE_RATE;
	}

	/**
	 * Creates a player at a specific (x,y) location
	 * @param x : The starting x coordinate (in pixel units)
	 * @param y : The starting y coordinate (in pixel units)
	 * @param input : The Keyboard controller for this player
	 */
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprites = PlayerSprite.playerReady;
		sprite = sprites[0];
		hitBox = new HealthBox(x - sprite.SIZE_X / 2, y - sprite.SIZE_Y / 2, sprite.SIZE_X, sprite.SIZE_Y, this);
		hitBox.init(level);
		attackBox.init(level);
		bounds = new Rectangle(x - sprite.SIZE_X / 2, y - sprite.SIZE_Y / 2, sprite.SIZE_X, sprite.SIZE_Y);
		fireRate = 0;//FireballProjectile.FIRE_RATE;
	}

	public Player(int x, int y, Keyboard input, boolean flip) {
		this.x = x;
		this.y = y;
		this.input = input;
		isFlipped = flip;
		control = flip ? 2 : 1;
		sprites = PlayerSprite.playerReady;
		sprite = sprites[0];
		hitBox = new HealthBox(x - sprite.SIZE_X / 2, y - sprite.SIZE_Y / 2, sprite.SIZE_X, sprite.SIZE_Y, this);
		hitBox.init(level);
		attackBox.init(level);
		bounds = new Rectangle(x - sprite.SIZE_X / 2, y - sprite.SIZE_Y / 2, sprite.SIZE_X, sprite.SIZE_Y);
		fireRate = 0;//FireballProjectile.FIRE_RATE;
	}

	/**
	 * Updates the player animation and moves the player when necessary
	 */
	public void update(int width, int height) {

		//if (fireRate > 0) fireRate--;

		// Increase the animation step, but don't let it increase indefinitely
		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (invincible) {
			if (invanim < 7500)
				invanim++;
			else
				invanim = 0;
		}
		// Update the change in position when a movement key is pressed
		int dx = 0, dy = 0;
		if (control == 1) {
			if (input.w)
				dy--;
			else if (input.s) dy++;
			if (input.a) dx--;
			if (input.d) dx++;

			if (input.space && spaceFree) {
				spaceFree = false;
				punching = true;
				ArrayList<Entity> me = new ArrayList<Entity>();
				me.add((Entity) this);
				me.add(hitBox);
				attackBox = new HitBox(x + bounds.width / 3, y - bounds.height / 4, 2 * bounds.width / 3, bounds.height / 2, true, 6, me);
				anim = 0;
				sprites = PlayerSprite.playerPunchRM;
				curFrame = 0;
				sprite = sprites[0];
			} else if (input.k && spaceFree) {
				spaceFree = false;
				kicking = true;
				ArrayList<Entity> me = new ArrayList<Entity>();
				me.add((Entity) this);
				me.add(hitBox);
				attackBox = new HitBox(x + bounds.width / 3, y, 2 * bounds.width / 3, bounds.height / 2, true, 4, me);
				anim = 0;
				sprites = PlayerSprite.playerKickLM;
				curFrame = 0;
				sprite = sprites[0];
			} else if (input.m && spaceFree) {
				spaceFree = false;
				dodging = true;
				sprites = PlayerSprite.playerDodge;
				curFrame = 0;
				sprite = sprites[0];
			} else if (!kicking && !punching && !input.m) {
				dodging = false;
				spaceFree = true;
				sprites = PlayerSprite.playerReady;
			}
		} else if (control == 2) {
			if (input.up)
				dy--;
			else if (input.down) dy++;
			if (input.left) dx--;
			if (input.right) dx++;

			if (input.period && spaceFree) {
				spaceFree = false;
				punching = true;
				ArrayList<Entity> me = new ArrayList<Entity>();
				me.add((Entity) this);
				me.add(hitBox);
				attackBox = new HitBox(x - bounds.width, y - bounds.height / 4, 2 * bounds.width / 3, bounds.height / 2, true, 6, me);
				anim = 0;
				sprites = PlayerSprite.playerPunchRM;
				curFrame = 0;
				sprite = sprites[0];
			} else if (input.enter && spaceFree) {
				spaceFree = false;
				kicking = true;
				ArrayList<Entity> me = new ArrayList<Entity>();
				me.add((Entity) this);
				me.add(hitBox);
				attackBox = new HitBox(x - bounds.width, y, 2 * bounds.width / 3, bounds.height / 2, true, 4, me);
				anim = 0;
				sprites = PlayerSprite.playerKickLM;
				curFrame = 0;
				sprite = sprites[0];
			} else if (input.zero && spaceFree) {
				spaceFree = false;
				dodging = true;
				sprites = PlayerSprite.playerDodge;
				curFrame = 0;
				sprite = sprites[0];
			} else if (!kicking && !punching && !input.zero) {
				dodging = false;
				spaceFree = true;
				sprites = PlayerSprite.playerReady;
			}
		}

		if (punching || kicking) {
			if (anim % 4 == 2) {
				curFrame++;
			}
			if (curFrame == 3 && attackBox.getLevel() == null) {
				attackBox.init(level);
				level.add(attackBox);
			}
			if (curFrame == 4) {
				level.remove(attackBox);
				attackBox = HitBox.nullBox;
			}
			if (curFrame >= sprites.length) {
				curFrame = 0;
				anim = 0;
				sprites = PlayerSprite.playerReady;
				spaceFree = true;
				punching = false;
				kicking = false;
			}
			sprite = sprites[curFrame];
		}
		if (invincible) {
			if (invanim % 20 == 19) {
				invincible = false;
				invanim = 0;
			}
		}

		// Move the player if its position will change, set walking flag accordingly
		if (spaceFree && dx != 0) {
			move(dx, 0, width, height);
			walking = true;
		} else {
			walking = false;
		}

		if (spaceFree && anim % 20 == 10) {
			curFrame = (curFrame + 1) % 2;
			if (curFrame > 1) curFrame = 0;
			sprite = sprites[curFrame];
		}

		hitBox.bounds.setLocation(x - bounds.width / 2, y - bounds.height / 2);
		bounds.setLocation(x - bounds.width / 2, y - bounds.height / 2);
		clear();
		updateShooting(width, height);
	}

	/**
	 * Clears projectiles which have been removed from the level.
	 */
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	/**
	 * Creates a new projectile when the player shoots.
	 * @param x : The X-Position of the projectile.
	 * @param y : The Y-Position of the projectile.
	 * @param dir : The direction where the projectile faces.
	 */
	protected void shoot(int x, int y, double dir) {
		//Projectile p = new FireballProjectile(x, y, dir);
		//level.addProjectile(p);
	}

	/**
	 * Determines if the player is shooting and fires a projectile in the appropriate direction.
	 */
	private void updateShooting(int width, int height) {

		/*// Shoot when mouse clicks
		if (Mouse.getB() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - (width / 2);
			double dy = Mouse.getY() - (height / 2);
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = FireballProjectile.FIRE_RATE;
		}*/
	}

	/**
	 *  Renders the player according to its direction and animation step
	 */
	public void render(Screen screen) {

		// Flip variable (0=none, 1=horizontal, 2=vertical, 3=both)
		int flip = isFlipped ? 1 : 0;

		// Change the sprite according to the player direction:

		// up
		if (dir == 0) {

		}
		// down
		if (dir == 2) {

		}
		// right
		if (dir == 1) {

		}
		// left
		if (dir == 3) {
			/*sprite = sprites[1][0];
			if (walking) {
				if (anim % 20 >= 10)
					sprite = sprites[1][1];
				else
					sprite = sprites[1][2];
			}
			flip = 1;*/
		}

		// Offset the position to center the player
		int xx = x - sprite.SIZE_X / 2;
		int yy = y - sprite.SIZE_Y / 2;

		// Render the player sprite
		screen.renderPlayer(xx, yy, sprite, flip);
	}

	public void takeDamage(int damage) {
		if (!invincible && !dodging) {
			health -= damage;
			System.out.println("Damage!\nPlayer " + control + ": " + health);
			invincible = true;
			invanim = 0;
		}
	}

	protected boolean collision(int dx, int dy, int width, int height) {
		boolean solid = false;
		if (x + sprite.SIZE_X / 2 + dx > width || x - sprite.SIZE_X / 2 + dx < 0) solid = true;
		if (y + sprite.SIZE_Y / 2 + dy > height || y - sprite.SIZE_Y / 2 + dy < 0) solid = true;
		hitBox.bounds.setLocation(x - bounds.width / 2 + dx, y - bounds.height / 2 + dy);
		bounds.setLocation(x - bounds.width / 2 + dx, y - bounds.height / 2 + dy);
		solid = level.collidesWith(hitBox) || solid;
		return solid;
	}
}
