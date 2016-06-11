package graphics;

import game.Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import level.Level;

public class SpriteSheet {

	private String path;
	public final int SIZE_X, SIZE_Y;
	public int[] pixels;

	public static SpriteSheet map = new SpriteSheet("/Res/background.png", 300, 169);
	public static SpriteSheet ready = new SpriteSheet("/Res/ready.png", 48, 51);
	public static SpriteSheet punchRM = new SpriteSheet("/Res/punch mid right.png", 156, 50);
	public static SpriteSheet kickLM = new SpriteSheet("/Res/kick mid left.png", 155, 50);

	/**
	 * Creates a sprite sheet from a resource path
	 * @param path : File path to image to create sprite sheet from
	 * @param size : Square size of the image to create sprite sheet from
	 */
	public SpriteSheet(String path, int sizex, int sizey) {
		this.path = path;
		SIZE_X = sizex;
		SIZE_Y = sizey;
		pixels = new int[SIZE_X * SIZE_Y];
		load();
	}

	/**
	 * Loads a sprite sheet from the image pointed to by the path
	 */
	private void load() {

		try {
			URL location = Game.class.getProtectionDomain().getCodeSource().getLocation();
			File file = new File(location.getFile());
			BufferedImage image = ImageIO.read(new File(file.getParentFile() + path));
			//BufferedImage image = ImageIO.read(Game.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
