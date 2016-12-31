package level;

import entity.Entity;
import entity.HitBox;
import entity.projectile.Projectile;
import graphics.Background;
import graphics.Screen;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Level {

	public static Level test = new Level(Background.test, 600, 300 / 16 * 10);

	public final int FRAMES;
	protected int width, height, curFrame = 0, anim = 0, speed = 20, step = 1;
	protected Background background;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

	public Level(Background background, int width, int height) {
		FRAMES = 1;//tilesOver.length;
		this.width = width;
		this.height = height;
		this.background = background;
	}

	/**
	 * Creates a new level
	 * @param width : The width of the level (in tile units)
	 * @param height : The height of the level (in tile units)
	 */
	public Level(int width, int height) {
		FRAMES = 1;
		this.width = width;
		this.height = height;
		background = Background.voidBackground;
		generateLevel();
	}

	/**
	 * Creates a level from a resource path
	 * @param path : File path to the image of the level
	 */
	public Level(String path) {
		FRAMES = 1;
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	@SuppressWarnings("unused")
	protected void loadLevel(String path) {
		URL location = Level.class.getProtectionDomain().getCodeSource().getLocation();
		File file = new File(location.getFile() + path);
	}

	/**
	 * Gets the projectile list for the level and returns it.
	 * @return A List<Projectile> object, containing the projectiles that have been added into the level
	 */
	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Updates the level by updating every entity within the level.
	 */
	public void update(int xScroll, int yScroll, Screen screen) {

		// Increase the animation step, but don't let it increase indefinitely
		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (anim % speed == speed - 1) curFrame += step;
		if (curFrame >= FRAMES) {
			step = -step;
			anim = 0;
			if (FRAMES != 1)
				curFrame = FRAMES - 2;
			else
				curFrame = FRAMES - 1;
		} else if (curFrame < 0) {
			step = -step;
			if (FRAMES != 1)
				curFrame = 1;
			else
				curFrame = 0;
		}

		background.update();

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	public void renderBackground(int x, int y, Screen screen) {

		background.render(x, y, screen);
	}

	public void renderTop(int xScroll, int yScroll, Screen screen) {

		// Tells the screen how much it is to be offset
		//screen.setOffset(xScroll, yScroll);

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
	}

	/**
	 * Adds an entity to the level.
	 * @param e : The entity to be added.
	 */
	public void add(Entity e) {
		entities.add(e);
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	/**
	 * Adds a projectile to the level, separate from other entities
	 * @param p : The projectile to be added.
	 */
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public boolean collidesWith(HitBox rect) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != rect && rect.contains(entities.get(i))) return true;
		}
		return false;
	}
}
