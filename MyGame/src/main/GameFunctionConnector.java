package main;

public class GameFunctionConnector implements Runnable {
   private GamePanel gamePanel;
   private Thread gThread;
    private final int FPS_SET = 120;
    public GameFunctionConnector() {
             gamePanel = new GamePanel();
            new Window(gamePanel);
            gamePanel.requestFocus();
            gameLoop();
    }
    private void gameLoop(){
        gThread = new Thread(this);
        gThread.start();

    }
    @Override
    public void run() {
        double timeFrame = 1000000000.0 / FPS_SET;
        long lastTime = System.nanoTime(), currentTime = System.nanoTime();
        int frames = 0;
        long lastCeck = System.currentTimeMillis();
        while(true){
            currentTime = System.nanoTime();
            if(currentTime - lastTime >= timeFrame){
                gamePanel.repaint();
                lastTime = currentTime;
                ++frames;
            }
            if(System.currentTimeMillis() - lastCeck >= 1000){
                lastCeck = System.currentTimeMillis();
                System.out.println("FPS :"  + frames + "\n");
                frames = 0;
    
            }
        }
    }
        
}
