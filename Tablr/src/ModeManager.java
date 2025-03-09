public class ModeManager {
    private static Mode mode = new TablesMode();

    public ModeManager(Mode mode) {
        mode = new TablesMode();
    }

    public static Mode getMode() {
        return mode;
    }

    public static String toTableDesignMode() {
        mode = new TableDesignMode();
        return "Table Design Mode";
    }

    public static String toTableRowsMode() {
        mode = new TableRowsMode();
        return "Table Rows Mode";
    }

    public static String toTablesMode() {
        mode = new TablesMode();
        return "Tables Mode";
    }
}