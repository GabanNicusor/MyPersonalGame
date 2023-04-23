package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestates;
import main.GamePanel;

//import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestates.states){
			case MENU:
			 gamePanel.getGame().getMenu().mouseClicked(e);
			 break;
			case PLAYING:
			gamePanel.getGame().getPlaying().mouseClicked(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
