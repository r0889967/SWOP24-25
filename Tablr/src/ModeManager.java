public class ModeManager {

    private static int mode = 0;

    //retrieve the current mode
    public static int getMode() {
        return mode;
    }

    //change the mode
    public static String setMode(int newMode) {
        mode = newMode;
        if(newMode==0){
            return "Tables mode";
        }else if(newMode==1){
            return "Table design mode";
        }else{
            return "Table rows mode";
        }

    }
}
