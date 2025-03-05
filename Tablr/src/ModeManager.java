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

    //return to tables mode if all cols are valid
    public static String toTablesMode(){
        Table table = TableManager.getSelectedTable();
        if (ErrorChecker.validTableName(table,TableManager.getTables())) {
            if(ErrorChecker.allValidColumns(table.getCols())) {
                mode = 0;
                table.unselectCol();
                return "Tables mode";
            }
        }
        return getMode();
    }

    //go to design mode if selected table name is valid or return to design mode if all cols are valid
    public static String toTableDesignMode(){
        Table table = TableManager.getSelectedTable();
        ArrayList<Column> cols = table.getCols();
        if (ErrorChecker.validTableName(table,TableManager.getTables())
        && ErrorChecker.allValidColumns(cols)) {
            mode = 1;
            return "Table design mode";
        }
        return getMode();
    }

    //go to rows mode if selected table name is valid and table is not empty or return to rows mode if selected col is valid
    public static String toTableRowsMode(){
        Table table = TableManager.getSelectedTable();
        Column col = table.getSelectedCol();
        ArrayList<Column> cols = table.getCols();
        if (ErrorChecker.validTableName(table,TableManager.getTables()) && !cols.isEmpty()
        && ErrorChecker.validColumn(col,cols)) {
            mode = 2;
            return "Table rows mode";
        }
        return getMode();
    }


}
