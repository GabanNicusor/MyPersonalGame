package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import input.Keyboard;
import input.Mouse;
 
public class GamePanel extends JPanel {
    private Mouse mouse; 
    private float YDelta = 0.04f, XDelta = 0.04f;
    private BufferedImage img, subImg;
        //We add every function or object we need:
    public GamePanel() {    
  
        mouse = new Mouse(this);      
        addImage();
        addKeyListener(new Keyboard(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        setSize();
     
    }
private void addImage() {
    InputStream in = getClass().getResourceAsStream("/res/playerDesing/player.png");
    try {
        img = ImageIO.read(in);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void changeYDelta(int x){
    this.YDelta += x;
    repaint();
}
public void changeXDelta(int x){
    this.XDelta += x;
    repaint();
}  
public void setPosition(int x, int y){
    this.XDelta = x;
    this.YDelta = y;
}
private void setSize(){
    Dimension size = new Dimension(1280, 800);
    setPreferredSize(size);
}
public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        subImg = img.getSubimage(0*32, 0*40, 32, 40);
        g.drawImage(subImg, (int)XDelta, (int)YDelta, 128, 160, null);
    }
}