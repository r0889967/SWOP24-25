public class ModeManager {

    private static int mode = 0;

    //retrieve the current mode
    public static String getMode() {
        if (mode == 0) {
            return "Tables mode";
        } else if (mode == 1) {
            return "Table design mode";
        } else {
            return "Table rows mode";
        }
    }

    //change the mode
    public static String setMode(int newMode) {
        Table table = TableManager.getSelectedTable();
        if (TableManager.hasValidName(table)) {
            mode = newMode;
            if (newMode == 0) {
                return "Tables mode";
            } else if (newMode == 1) {
                return "Table design mode";
            } else {
                return "Table rows mode";
            }
        }
        return getMode();
    }


}
