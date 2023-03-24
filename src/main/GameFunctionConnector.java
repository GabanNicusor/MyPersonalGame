package main;

public class GameFunctionConnector implements Runnable {
    private GamePanel gamePanel;
    private Thread gThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 1000;

    public GameFunctionConnector() {
        gamePanel = new GamePanel();
        new Window(gamePanel);
        gamePanel.requestFocus();
        gameLoop();
    }

    private void gameLoop() {
        gThread = new Thread(this);
        gThread.start();
    }

    public void update() {
        gamePanel.updateGame();
    }

    @Override
    public void run() {
        double timeFrame = 1000000000.0 / FPS_SET;
        double timeUpdate = 1000000000.0 / UPS_SET;
        int frames = 0, update = 0;
        long lastCeck = System.currentTimeMillis(), previousTime = System.nanoTime();
        double deltaU = 0, deltaF = 0;
        while (true) {
            long currentTimeUpdate = System.nanoTime();
            deltaU += (currentTimeUpdate - previousTime) / timeUpdate;
            deltaF += (currentTimeUpdate - previousTime) / timeFrame;
            previousTime = currentTimeUpdate;
            if (deltaF >= 1) {
                gamePanel.repaint();
                ++frames;
                deltaF--;
            }
            if (deltaU >= 1) { 
                update();
                ++update;
                --deltaU;
            }
            if (System.currentTimeMillis() - lastCeck >= 1000) {
                lastCeck = System.currentTimeMillis();
                System.out.println("FPS :"  + frames + "  UPS : " + update + "\n");
                frames = 0;
                update = 0;
            }
        }
    }    
}
