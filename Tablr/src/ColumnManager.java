import java.lang.reflect.Array;
import java.util.ArrayList;

public class ColumnManager {

    private static int sequenceNumber = 0;

    private static String generateName(){
        sequenceNumber++;
        return "Column"+sequenceNumber;
    }

    public static boolean hasValidName(Column col){
        Table table = TableManager.getSelectedTable();

        if(col == null){
            return true;
        }
        if(col.getName().equals("")){
            return false;
        }
        for(Column c:getCols(table)){
            if(c==col){
                continue;
            }
            if(c.getName().equals(col.getName())){
                return false;
            }
        }
        return true;
    }


    public static void selectCol(Table table,int idx){
        ArrayList<Column> cols = table.getCols();
        if(hasValidName(getSelectedCol())) {
            if (getSelectedCol() != null) {
                getSelectedCol().unselect();
            }
            cols.get(idx).select();
        }
    }

    public static void unselectCol(){
        if(hasValidName(getSelectedCol())) {
            if (getSelectedCol() != null) {
                getSelectedCol().unselect();
            }
        }
    }

    public static Column getSelectedCol(){
        ArrayList<Column> cols = TableManager.getSelectedTable().getCols();
        for(int i=0;i<cols.size();i++){
            if(cols.get(i).isSelected()){
                return cols.get(i);
            }
        }
        return null;
    }

    public static void editColName(char keyChar){
        Column col = getSelectedCol();
        String name = col.getName();
        if(keyChar=='\b'){
            col.setName(name.substring(0, name.length()-1));
        }else{
            col.setName(name+keyChar);
        }
    }

    public static void editColType(Column col){

    }

    public static ArrayList<Column> getCols(Table table){
        return table.getCols();
    }

    //create and add a new col
    public static void createAndAddCol(){
        if(hasValidName(getSelectedCol())) {
            Table table = TableManager.getSelectedTable();
            String name = generateName();
            Column col = new Column(name);
            table.addCol(col);
        }
    }

    //delete selected col
    public static void deleteCol(){
        Table table = TableManager.getSelectedTable();
        if(getSelectedCol()!=null) {
            table.deleteCol(getSelectedCol());
        }
    }
}
