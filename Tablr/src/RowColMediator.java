import java.util.ArrayList;

public class RowColMediator {

    //delete corresponding cells of rows when a col is deleted
    public static void synchronize(Table table, int idx){
        ArrayList<Row> rows = table.getRows();
        for(Row row : rows){
            row.deleteCell(idx);
            if(row.getCells().isEmpty()){
                table.deleteRow(row);
            }
        }
    }

    //add corresponding cells to rows when a col is added
    public static void synchronize2(Table table){
        ArrayList<Row> rows = table.getRows();
        for(Row row : rows){
            row.addCell(new Cell(""));
        }
    }

    public static ArrayList<Column> getCols(Table table){
        return table.getCols();
    }

    public static ArrayList<Row> getRows(Table table){
        return table.getRows();
    }





}

