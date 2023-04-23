package Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Spike {
    Game game;
    public final static int TEPI_DEFAULT_SIZE = 98;
    public final static float TEPI_SCALE = (int) TEPI_DEFAULT_SIZE * Game.SCALE;
    private static final int DEFAULT_POSITION_X = 510 * (int)Game.SCALE;
    private static final int DEFAULT_POSITION_Y = 350 * (int)Game.SCALE;
    public Spike(Game game) {
        this.game = game;
    }
    public void drawTepi(Graphics g) {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Level_Tepi);
			g.drawImage(img,(int)DEFAULT_POSITION_X, (int)DEFAULT_POSITION_Y, (int)Game.TEPI_SCALE, (int)Game.TEPI_SCALE, null);

    }
    public void update() {
    }
}

