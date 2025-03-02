import java.util.ArrayList;

public class RowManager {

    public static void selectCell(Table table,int x,int y){

    }

    public static void unselectCell() {

    }

    public static Row getSelectedCell(){
        return null;
    }



    public static void createAndAddRow() {
        boolean allColsValid = true;
        Table table = TableManager.getSelectedTable();

        for (Column c : ColumnManager.getCols(table)) {
            if (!ColumnManager.isColValid(c)) {
                allColsValid = false;
                break;
            }
        }

        if (allColsValid) {
            Row row = new Row();
            for (Column c : ColumnManager.getCols(table)) {
                row.addCell(new Cell(c.getDefaultValue()));
            }
            table.addRow(row);
        }


    }

}