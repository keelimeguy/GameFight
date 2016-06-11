package graphics;

public class Background {

	public final int SIZE_X, SIZE_Y;
	public Sprite sprite;

	public static Background voidBackground = new Background(50, 50, 0xFFFFFF);

	public static Background test = new Background(300, 169, 0, 0, SpriteSheet.map);

	/**
	 * Creates a sprite from a sprite sheet
	 * @param size : Square size of the sprite (in pixels)
	 * @param x : The x coordinate of the sprite in the sprite sheet (in size units)
	 * @param y : The y coordinate of the sprite in the sprite sheet (in size units)
	 * @param sheet : The sprite sheet to take the sprite image from
	 */
	public Background(int size, int x, int y, SpriteSheet sheet) {
		SIZE_X = SIZE_Y = size;
		sprite = new Sprite(size, x, y, sheet);
	}

	public Background(int sizex, int sizey, int x, int y, SpriteSheet sheet) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		sprite = new Sprite(sizex, sizey, x, y, sheet);
	}

	/**
	 * Creates a sprite of a given color
	 * @param size : Square size of the sprite (in pixels)
	 * @param color : The color to fill the sprite
	 */
	public Background(int size, int color) {
		SIZE_X = SIZE_Y = size;
		sprite = new Sprite(size, color);
	}

	public Background(int sizex, int sizey, int color) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		sprite = new Sprite(sizex, sizey, color);
	}

	/**
	 * Creates a sprite from a resource path
	 * @param sizeX : Horizontal size of the sprite (in pixels)
	 * @param sizeY : Vertical size of the sprite (in pixels)
	 * @param path : The path source to take the sprite image from
	 */
	public Background(int sizex, int sizey, String path) {
		SIZE_X = sizex;
		SIZE_Y = sizey;
		sprite = new Sprite(sizex, sizey, path);
	}

	public void update() {

	}

	public void render(int x, int y, Screen screen) {
		screen.renderSprite(sprite, x, y);
	}
}
