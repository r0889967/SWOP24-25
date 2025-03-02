import java.util.ArrayList;

public class RowManager {

    public static void selectCell(Table table,int x,int y){

    }

    public static void unselectCell() {

    }

    public static Row getSelectedCell(){
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