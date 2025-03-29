public class SubWindowManager {
    private static SubWindow subWindow = new TablesWindow(new TableManager());

    public static SubWindow getWindow() {
        return subWindow;
    }

    /**
     * Switches window for table designing
     */
    public static void toTableDesignWindow(TableManager tableManager) {
        subWindow = new TableDesignWindow(tableManager);
    }

    /**
     * Switches window for table editing
     */
    public static void toTableRowsWindow(TableManager tableManager) {
        subWindow = new TableRowsWindow(tableManager);
    }

    /**
     * Switches window for table management
     */
    public static void toTablesWindow(TableManager tableManager) {
        subWindow = new TablesWindow(tableManager);
    }
}