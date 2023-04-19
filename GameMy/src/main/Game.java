package main;

import java.awt.Graphics;

import gamestates.Gamestates;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {
// Add the necesary things and set the speed for our game
	 GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
// Tile size and Render Scale
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
// Tepi TILE and SCALE
    public final static int TEPI_DEFAULT_SIZE = 98;
    public final static float TEPI_SCALE = (int) TEPI_DEFAULT_SIZE * Game.SCALE;
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}
// Add and give the start position for player
	public void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}
// Start the game thread game loop
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
// Call the update methods
	public void update() {
		switch (Gamestates.states) {
			case MENU:
				menu.Update();
				break;
			case PLAYING:
				playing.Update();
				break;
			default:
				break;
	
			}
	}
// Render the map and player
	public void render(Graphics g) {	
		switch (Gamestates.states) {
			case MENU:
				menu.Draw(g);
				break;
			case PLAYING:
				playing.Draw(g);
				break;
			default:
				break;
			}
	}
// Run until the game is finish and set a speed for game engine and player animation
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
// Reset the movement action to false in case we press outside of our game window 
	public void windowFocusLost() {
		if(Gamestates.states == Gamestates.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
    public Menu getMenu() {
		return menu;
	}
	public Playing getPlaying() {
		return playing;
	}
}