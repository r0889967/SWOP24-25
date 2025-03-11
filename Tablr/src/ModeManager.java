public class ModeManager {
    private static Mode mode = new TablesMode();

    public ModeManager(Mode mode) {
        mode = new TablesMode();
    }

    public static Mode getMode() {
        return mode;
    }

    //Switches mode for table designing
    public static String toTableDesignMode() {
        mode = new TableDesignMode();
        return "Table Design Mode";
    }

    //Switches mode for table editing
    public static String toTableRowsMode() {
        mode = new TableRowsMode();
        return "Table Rows Mode";
    }

    //Switches mode for table management
    public static String toTablesMode() {
        mode = new TablesMode();
        return "Tables Mode";
    }
}