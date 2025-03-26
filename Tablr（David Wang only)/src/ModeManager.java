public class ModeManager {
    private static Mode mode = new TablesMode(new TableManager());

    public static Mode getMode() {
        return mode;
    }

    /**
     * Switches mode for table designing
     */
    public static void toTableDesignMode(TableManager tableManager) {
        mode = new TableDesignMode(tableManager);
    }

    /**
     * Switches mode for table editing
     */
    public static void toTableRowsMode(TableManager tableManager) {
        mode = new TableRowsMode(tableManager);
    }

    /**
     * Switches mode for table management
     */
    public static void toTablesMode(TableManager tableManager) {
        mode = new TablesMode(tableManager);
    }
}