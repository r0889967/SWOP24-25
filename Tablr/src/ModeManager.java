import java.util.ArrayList;

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
        if (ErrorChecker.validTableName(table,TableManager.getTables())) {
            if(ErrorChecker.validColumn(col, ColumnManager.getCols(table))) {
                mode = 0;
                ColumnManager.unselectCol();
                return "Tables mode";
            }
        }
        return getMode();
    }

    public static String toTableDesignMode(){
        Table table = TableManager.getSelectedTable();
        if (ErrorChecker.validTableName(table,TableManager.getTables())) {
            mode = 1;
            return "Table design mode";
        }
        return getMode();
    }

    public static String toTableRowsMode(){
        Table table = TableManager.getSelectedTable();
        Column col = ColumnManager.getSelectedCol();
        ArrayList<Column> cols = ColumnManager.getCols(table);
        if (ErrorChecker.validTableName(table,TableManager.getTables())) {
            if(ErrorChecker.validColumn(col, cols) && !cols.isEmpty()) {
                mode = 2;
                ColumnManager.unselectCol();
                return "Table rows mode";
            }
        }
        return getMode();
    }


}
