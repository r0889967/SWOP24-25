package old;
import java.util.ArrayList;

public class TableManager {
    private static final int maxTablePerRow = 6;
    private static final int maxTablePerCol = 10;
    private static final int maxTables = maxTablePerRow*maxTablePerCol;
    private static final ArrayList<Table> tables = new ArrayList<Table>();
    private static int sequenceNumber = 0;


    //generate random name for table
    private static String generateName(){
        sequenceNumber++;
        return "Table"+sequenceNumber;
    }

    public static int getMaxTablePerRow(){
        return maxTablePerRow;
    }

    public static int getMaxTablePerCol(){
        return maxTablePerCol;
    }

    //select table with idx
    public static void selectTable(int idx){
        Table table = getSelectedTable();
        if(ErrorChecker.validTableName(table,tables)) {
            if(table!=null) {
                table.unselect();
            }
            if(idx<tables.size()) {
                tables.get(idx).select();
            }
        }
    }

    public static void unselectTable(){
        Table table = getSelectedTable();
        if(ErrorChecker.validTableName(table,tables)) {
            if (table != null) {
                table.unselect();
            }
        }
    }

    //retrieve selected table
    public static Table getSelectedTable(){
        for (Table table : tables) {
            if (table.isSelected()) {
                return table;
            }
        }
        return null;
    }

    //edit selected table's name
    public static void editTableName(char keyChar){
        Table table = getSelectedTable();
        if(table!=null) {
            table.editName(keyChar);
        }
    }

    public static void undoEditName(){
        Table table = getSelectedTable();
        if(table!=null) {
            table.restoreOldName();
        }
    }

    public static void saveNewName(){
        Table table = getSelectedTable();
        if(ErrorChecker.validTableName(table,tables)) {
            if (table != null) {
                table.saveName();
            }
        }
    }


    //retrieve all tables
    public static ArrayList<Table> getTables(){
        return tables;
    }

    //create and add a new table
    public static void createAndAddTable(){
        Table table = getSelectedTable();
        if(ErrorChecker.validTableName(table,tables)) {
            if (tables.size() < maxTables) {
                String name = generateName();
                Table table_ = new Table(name);
                while(!ErrorChecker.validTableName(table_,tables)) {
                    name = generateName();
                    table_.setBothNames(name);
                }
                tables.add(table_);
            }
        }
    }

    //delete selected table
    public static void deleteTable(){
        Table table = getSelectedTable();
        tables.remove(table);
    }


}
