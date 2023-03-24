package main;
import javax.swing.JFrame;
public class Window extends JFrame {
    JFrame Jframe;
    public Window(GamePanel gamePanel) {
        Jframe = new JFrame(); 
        Jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
         Jframe.add(gamePanel);
         Jframe.setLocationRelativeTo(null);   
         Jframe.setResizable(false);
         Jframe.pack();
        Jframe.setVisible(true);
    }
}
