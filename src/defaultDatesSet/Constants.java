package defaultDatesSet;

public class Constants {
    // SET KEY ACTION
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    // SET ANIMATION ACTION
    public static class keyAnimationPressed {
        public static final int STANDBY = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        // Retun size column for each key pressed
        public static int GetKeyAni(int player_key) {
            switch(player_key) {
                case STANDBY:
                return 5;
                case RUNNING:
                return 6;
                case JUMP:
                return 3;
                case FALL:
                return 1;
                default:
                return 1;
            }
        }
    }  
}
