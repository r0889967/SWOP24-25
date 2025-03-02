import java.util.ArrayList;

public class RowColMediator {

    public static void synchronize(Table table, int idx){
        ArrayList<Row> rows = table.getRows();
        for(Row row : rows){
            row.deleteCell(idx);
            if(row.getCells().isEmpty()){
                table.deleteRow(row);
            }
        }
    }

    public static void synchronize2(Table table){
        ArrayList<Row> rows = table.getRows();
        for(Row row : rows){
            row.addCell(new Cell(""));
        }
    }





}

