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
import static defaultDatesSet.Constants.keyAnimationPressed.*;
import static defaultDatesSet.Constants.Direction.*;
 
public class GamePanel extends JPanel {
    private Mouse mouse; 
    private float YDelta = 0.04f, XDelta = 0.04f;
    private BufferedImage img;
    private BufferedImage[][] animationCharacter;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 10;
    private int playerAction = STANDBY, playerDirection = -1;
    private boolean moving = false;
    // WE ADD EVERY OBJECT OR FUNCTION WE NEED
    public GamePanel() {    
        mouse = new Mouse(this);      
        addImage();
        repetitivePrincipalMovement();
        addKeyListener(new Keyboard(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        setSize();

     
    }
    // WE EXTRACT FROM OUR IMAGE EACH ANIMATION WE WANT TO USE
    private void repetitivePrincipalMovement() {
        animationCharacter = new BufferedImage[9][6];
        for (int j = 0; j < animationCharacter.length; ++j) {
            for (int i = 0; i < animationCharacter[j].length; ++i) {
                animationCharacter[j][i] = img.getSubimage(i*64, j*40, 64, 40);
            }
        }
    }
    // WE ADD THE IMAGE
    private void addImage() {
        InputStream in = getClass().getResourceAsStream("/res/playerDesing/player.png");
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // WE SET THE DIRECTION WE WANT TO GO USING THE KEYBOARD
    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }
    // WE SET THE MOVEMENT STATEMENT
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    // WE SET A DEFAULT SIZE OF OUR WINDOW
    private void setSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }
    // WE DISPLAY THE IMAGE FOR A SPECIFIC LINE AND COLUMN USING paintComponent
    public void updateAnimationTick() {
        ++aniTick;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            ++aniIndex;
        if (aniIndex >= GetKeyAni(playerAction)) {
            aniIndex = 0;
        }
    }
}
    // WE SET A DEFAULT ANIMATION
    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        }else{
            playerAction  = STANDBY;
        }
    }
    // WE UPDATE THE POSITIONS FOR A SPECIFIC KeyPressed
    private void updatePosition() {
        if (moving) {
            switch(playerDirection){
                case UP:
                    YDelta -= 5;
                    break;
                case LEFT:
                    XDelta -= 5;
                    break;
                case RIGHT:
                    XDelta += 5;
                    break;
                case DOWN:
                    YDelta += 5;
                    break;
        }
    }
}
    // HERE HE UPDATE THE CHARACTER POSITION AND THE GAME ANIMATION
    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        g.drawImage(animationCharacter[playerAction][aniIndex], (int)XDelta, (int)YDelta, 128, 80, null);
    }
}