import java.util.ArrayList;

public class RowManager {


    public static void selectCell(Table table,int ridx,int cidx){
        Cell cell = getSelectedCell();

        if (cell != null) {
            cell.unselect();
        }
        if (ridx < getRows(table).size()) {
            getRows(table).get(ridx).getCells().get(cidx).select();
        }

    }


    public static void unselectCell() {
        Row r = getSelectedRow();
        Cell cell = getSelectedCell();

        if (r != null) {
            for (Cell c : r.getCells()) {
                if (c.isSelected()) {
                    c.unselect();
                    break;
                    }
                }
            }

    }

    public static Cell getSelectedCell(){
        Table table = TableManager.getSelectedTable();
        for(Row row : getRows(table)) {
            for (Cell c : row.getCells()) {
                if (c.isSelected()) {
                    return c;
                }
            }
        }
        return null;
    }

    public static void editCellValue(char keyChar){
        Cell cell = getSelectedCell();
        String value = cell.getValue();
        if(keyChar=='\b'){
            cell.setValue(value.substring(0, value.length()-1));
        }else{
            cell.setValue(value+keyChar);
        }
    }

    public static void selectRow(Table table,int idx){
        Row row = getSelectedRow();
        if (row != null) {
            row.unselect();
        }
        if(idx<getRows(table).size()) {
            getRows(table).get(idx).select();
        }
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
            for (Row row : rows) {
                if (row.isSelected()) {
                    return row;
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
        for (Column c : table.getCols()) {
            row.addCell(c.getDefaultValue());
        }
        table.addRow();
    }

    public static void deleteRow(){
        Table table = TableManager.getSelectedTable();
        Row row = getSelectedRow();
        if(row!=null) {
            table.deleteRow(row);
        }
    }

}