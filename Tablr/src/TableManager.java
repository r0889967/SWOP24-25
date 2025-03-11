import java.util.ArrayList;

public class TableManager {
    private static final int maxTablePerRow = 1;
    private static final int maxTablePerCol = 20;
    private static final int maxTables = maxTablePerRow*maxTablePerCol;
    private static final ArrayList<Table> tables = new ArrayList<Table>();
    private static int sequenceNumber = 1;


    //generate incremental name for table
    private static String generateName(){
        return "Table"+sequenceNumber++;
    }

    public static int getMaxTablePerRow(){
        return maxTablePerRow;
    }

    public static int getMaxTablePerCol(){
        return maxTablePerCol;
    }

    //get table by index
    public static Table getTableByIndex(int idx){
        return tables.get(idx);
    }

    //select table with idx
    public static void selectTable(int idx){
        Table table = getSelectedTable();
        if(validTableName(table)) {
            if(table!=null) {
                table.unselect();
            }
            if(idx<tables.size()) {
                tables.get(idx).select();
            }
        }
    }

    //unselect the currently selected table
    public static void unselectTable(){
        Table table = getSelectedTable();
        if(validTableName(table)) {
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

    //restore old name of selected table
    public static void undoEditName(){
        Table table = getSelectedTable();
        if(table!=null) {
            table.restoreOldName();
        }
    }

    //save selected table name and deleted old name
    public static void saveNewName(){
        Table table = getSelectedTable();
        if(validTableName(table)) {
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
        if(validTableName(table)) {
            if (tables.size() < maxTables) {
                String name = generateName();
                Table table_ = new Table(name);
                while(!validTableName(table_)) {
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


    //check if table has valid name
    public static boolean validTableName(Table table){
        if(table == null){
            return true;
        }
        if(table.getName().isEmpty()){
            return false;
        }
        if (!uniqueTableName(table)){
            return false;
        }
        return true;
    }

    // check if table name is unique
    public static boolean uniqueTableName(Table table){
        for(Table t:tables){
            if(t==table){
                continue;
            }
            if(t.getName().equals(table.getName())){
                return false;
            }
        }
        return true;
    }

    //reset static table manager state only for testing purposes
    public static void resetState(){
        tables.clear();
        sequenceNumber = 1;
    }
}
