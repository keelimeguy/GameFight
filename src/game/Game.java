package game;

import graphics.Screen;
import input.Keyboard;
import input.Mouse;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import level.Level;
import sound.MusicPlayer;
import sound.SoundPlayer;
import entity.mob.Player;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3; // The game will be scaled up by this factor, so the actual window width and height will be the above values times this value
	public static String title = "2DGame";

	public int anim = 0, speed = 20, step = 0;

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player[] player;
	private boolean running = false;

	private Screen screen;
	public static MusicPlayer snd;
	public static SoundPlayer noise;

	// The image which will be drawn in the game window
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/**
	 * Initiates the necessary variables of the game
	 */
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.test;

		define();

		snd = new MusicPlayer();
		noise = new SoundPlayer();

		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	private void define() {
		player = new Player[2];
		player[0] = new Player(screen.width / 3, screen.height / 2, key);
		player[0].init(level);
		player[1] = new Player(screen.width * 2 / 3, screen.height / 2, key, true);
		player[1].init(level);
		level.add(player[0].hitBox);
		level.add(player[1].hitBox);
	}

	/**
	 * Returns the height of the window with scaling.
	 * @return The width as an int value
	 */
	public int getWindowWidth() {
		return frame.getContentPane().getWidth();
	}

	/**
	 * Returns the height of the window with scaling.
	 * @return The height as an int value
	 */
	public int getWindowHeight() {
		return frame.getContentPane().getHeight();
	}

	/**
	 * Starts the game thread
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
		snd.start();
	}

	/**
	 * Stops the game thread
	 */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			snd.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs the game
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0, updates = 0;
		requestFocus();

		//String audioFilePath = "/Music/child.wav";
		//snd.playMusic(audioFilePath, LoopStart.CHILD, -1);

		// The game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			// Update 60 times a second
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			Graphics g = getGraphics();
			paint(g);
			frames++;

			// Keep track of and display the game's ups and fps every second
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + " | ups: " + updates + ", fps: " + frames);
				updates = 0;
				frames = 0;
			}
		}

		// If we get out of the game loop stop the game
		stop();
	}

	/**
	 * Update the game
	 */
	public void update() {
		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (anim % speed == speed - 1) step++;
		/*if (key.shift && step >= 1) {
			if (level == Level.test) {
				String audioFilePath = "/Music/epic.wav";
				snd.reset();
				snd.playMusic(audioFilePath, LoopStart.EPIC, -1);
				level = Level.fire;
			} else if (level == Level.fire) {
				String audioFilePath = "/Music/funky.wav";
				snd.reset();
				snd.playMusic(audioFilePath, LoopStart.FUNKY, -1);
				level = Level.water;
			} else if (level == Level.water) {
				String audioFilePath = "/Music/child.wav";
				snd.reset();
				snd.playMusic(audioFilePath, LoopStart.CHILD, -1);
				level = Level.test;
			}
			step = anim = 0;
			define();
		}*/
		int xScroll = 0;//player.x - screen.width / 2;
		int yScroll = 0;//player.y - screen.height / 2;
		key.update();
		player[0].update(screen.width, screen.height);
		player[1].update(screen.width, screen.height);
		level.update(xScroll, yScroll, screen);
	}

	/**
	 * Render the game
	 */

	public void update(Graphics g) {
	}

	public void paint(Graphics g) {
		// Clear the screen to black before rendering
		screen.clear(0);

		int xScroll = 0;//player.x - screen.width / 2;
		int yScroll = 0;//player.y - screen.height / 2;

		level.renderBackground(0, 0, screen);

		// Render the player
		player[0].render(screen);
		player[1].render(screen);

		level.renderTop(xScroll, yScroll, screen);

		// Copy the screen pixels to the image to be drawn
		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

		Graphics gi = image.getGraphics();
		gi.setColor(Color.GREEN);
		gi.setFont(new Font(Font.SERIF, 10, 10));
		gi.drawString("Player 1: " + player[0].health, 10, 10);
		gi.drawString("Player 2: " + player[1].health, 230, 10);
		gi.dispose();

		// Draw the image
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
	}

	/**
	 * Start of the program
	 * @param args : Unused default arguments
	 */
	public static void main(String[] args) {
		System.setProperty("sun.awt.noerasebackground", "true");
		// Create the game
		Game game = new Game();
		game.frame.setResizable(true);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		// Start the game
		game.start();
	}
}
