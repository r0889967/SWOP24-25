public class ModeManager {

    private static int mode = 0;

    //retrieve the current mode
    public static int getMode() {
        return mode;
    }

    //change the mode
    public static void setMode(int newMode) {
        mode = newMode;
    }
}
