package input;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

import main.GamePanel;

public class Mouse implements MouseInputListener {
    //private GamePanel gamePanel;
    public Mouse(GamePanel gamePanel) {
        //this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("is pressed");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("is released");

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
   
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) { 
    }
    
}
