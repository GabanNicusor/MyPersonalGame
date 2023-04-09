package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {

	 GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 200;
	private final int UPS_SET = 200;
	private Player player;
	private LevelManager levelManager;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}
// initializes the necessary classes
	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
	}
// Set and start more thread to work on to have a good flow in the game
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
// Update the necessary classes
	public void update() {
		levelManager.update();
		player.update();
	}
// Access the necessary desing for player and level
	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
	}
// Use a Run function to set an speed for player and system
	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}
// Reset the key function through this function
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}