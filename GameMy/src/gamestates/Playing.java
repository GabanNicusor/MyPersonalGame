package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

public class Playing extends States implements gameStatesMethods {
    private Player player;
	private LevelManager levelManager;
    private final static float DEFAULT_POSITION_X = 510 * Game.SCALE;
	private final static float DEFAULT_POSITION_Y = 350 * Game.SCALE;

	public Playing(Game game) {
		super(game);
		initClasses();
	}
    public void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 290, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

	}
	@Override
    public void Update() {
        levelManager.update();
        player.update();
    }

    @Override
    public void Draw(Graphics g) {
        levelManager.draw(g);
        drawTepi(g);
        player.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
                case KeyEvent.VK_BACK_SPACE:
                Gamestates.states = Gamestates.MENU;
			initClasses();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
			    player.setRight(false);
			    break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
		}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
        player.setAttacking(true);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
    public void windowFocusLost() {
		player.resetDirBooleans();
	}
	public void drawTepi(Graphics g) {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Level_Tepi);
			g.drawImage(img,(int)DEFAULT_POSITION_X, (int)DEFAULT_POSITION_Y, (int)Game.TEPI_SCALE, (int)Game.TEPI_SCALE, null);
	}
// Return the player data
	public Player getPlayer() {
		return player;
	}
}
