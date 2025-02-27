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

    public static String toTablesMode(){
        Table table = TableManager.getSelectedTable();
        Column col = ColumnManager.getSelectedCol();
        if (TableManager.hasValidName(table)) {
            if(ColumnManager.hasValidName(col)) {
            mode = 0;
            return "Tables mode";
            }
        }
        return getMode();
    }

    public static String toTableDesignMode(){
        Table table = TableManager.getSelectedTable();
        if (TableManager.hasValidName(table)) {
            mode = 1;
            return "Table design mode";
        }
        return getMode();
    }

    public static String toTableRowsMode(){
        Table table = TableManager.getSelectedTable();
        Column col = ColumnManager.getSelectedCol();
        if (TableManager.hasValidName(table)) {
            if(ColumnManager.hasValidName(col)) {
                mode = 2;
                return "Table rows mode";
            }
        }
        return getMode();
    }


}
