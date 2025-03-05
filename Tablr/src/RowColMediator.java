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


}

