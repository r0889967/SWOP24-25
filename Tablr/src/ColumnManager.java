import java.lang.reflect.Array;
import java.util.ArrayList;

public class ColumnManager {

    private static int sequenceNumber = 0;

    private static String generateName(){
        sequenceNumber++;
        return "Column"+sequenceNumber;
    }


    public static void selectCol(Table table,int idx){
        ArrayList<Column> cols = table.getCols();
        for(int i=0;i<cols.size();i++){
            if(i==idx){
                cols.get(i).select();
            }else{
                cols.get(i).unselect();
            }
        }
    }

    public static Column getSelectedCol(Table table){
        ArrayList<Column> cols = table.getCols();
        for(int i=0;i<cols.size();i++){
            if(cols.get(i).isSelected()){
                return cols.get(i);
            }
        }
        return null;
    }

    public static void editColName(Column col,char keyChar){
        String name = col.getName();
        if(keyChar=='\b'){
            col.setName(name.substring(0, name.length()-1));
        }else{
            col.setName(name+keyChar);
        }
    }

    public static ArrayList<Column> getCols(Table table){
        return table.getCols();
    }

    //create and add a new col
    public static void createAndAddCol(Table table){
        String name = generateName();
        Column col = new Column(name);
        table.addCol(col);


    }

    //delete selected col
    public static void deleteCol(Table table,Column col){
        table.deleteCol(col);
    }
}
