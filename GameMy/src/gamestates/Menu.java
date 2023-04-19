package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;

public class Menu extends States implements gameStatesMethods{
     Menu menu;
    public Menu(Game game) {
        super(game);
    }

    @Override
    public void Update() {
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.black);
		g.drawString("MENU", Game.GAME_WIDTH / 2, 200);
    }

    @Override
    public void keyPressed(KeyEvent e) { 
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestates.states = Gamestates.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
}
