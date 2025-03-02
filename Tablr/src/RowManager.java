import java.util.ArrayList;

public class RowManager {

    public static void selectCell(Table table,int row,int col){


    }

    public static void unselectCell() {

    }

    public static Row getSelectedCell(){
        return null;
    }

    public static void selectRow(Table table,int rowidx){
        Row row = getSelectedRow();
        if (row != null) {
            row.unselect();
        }
        getRows(table).get(rowidx).select();
    }

    public static void unselectRow(){
        Row row = getSelectedRow();
        if (row != null) {
            row.unselect();
        }
    }

    public static Row getSelectedRow(){
        Table table = TableManager.getSelectedTable();
        if(table!=null) {
            ArrayList<Row> rows = getRows(table);
            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).isSelected()) {
                    return rows.get(i);
                }
            }
        }
        return null;

    }

    public static ArrayList<Row> getRows(Table table){
        return table.getRows();
    }



    public static void createAndAddRow() {
        Table table = TableManager.getSelectedTable();
        Row row = new Row();
        for (Column c : ColumnManager.getCols(table)) {
            row.addCell(new Cell(c.getDefaultValue()));
        }
        table.addRow(row);
    }

}