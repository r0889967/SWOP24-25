import java.util.ArrayList;

public class TableManager {
    private static int maxTablePerRow = 6;
    private static int maxTablePerCol = 10;
    private static int maxTables = maxTablePerRow*maxTablePerCol;
    private static ArrayList<Table> tables = new ArrayList<Table>();
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
            tables.get(idx).select();
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
        for(int i=0;i<tables.size();i++){
            if(tables.get(i).isSelected()){
                return tables.get(i);
            }
        }
        return null;
    }


    //edit selected table's name
    public static void editTableName(char keyChar){
        Table table = getSelectedTable();
        if(table!=null) {
            String name = table.getName();

            if (keyChar == '\b') {
                table.setName(name.substring(0, name.length() - 1));
            } else {
                table.setName(name + keyChar);
            }
        }
    }

    public static void undoEditName(){
        Table table = getSelectedTable();
        if(table!=null) {
            String oldName = table.getOldName();
            table.setName(oldName);
        }
    }

    public static void saveNewName(){
        Table table = getSelectedTable();
        if(ErrorChecker.validTableName(table,tables)) {
            if (table != null) {
                String newName = table.getName();
                table.setOldName(newName);
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
                while(!ErrorChecker.validTableName(table,tables)) {
                    name = generateName();
                    table_.setName(name);
                    table_.setOldName(name);
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
