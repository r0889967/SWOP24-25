import java.util.ArrayList;

public class TableManager {
    private  final int maxTablePerRow = 1;
    private  final int maxTablePerCol = 20;
    private  final int maxTables = maxTablePerRow*maxTablePerCol;
    private  final ArrayList<Table> tables = new ArrayList<Table>();
    private Table selectedTable = null;
    private  int sequenceNumber = 1;


    /**
     * generate incremental name for table
     */
    private  String generateName(){
        return "Table"+sequenceNumber++;
    }

    public  int getMaxTablePerRow(){
        return maxTablePerRow;
    }

    public  int getMaxTablePerCol(){
        return maxTablePerCol;
    }

    /**
     * get table by index
     */
    public  Table getTableByIndex(int idx){
        return tables.get(idx);
    }

    /**
     * select table with idx
     */
    public  void selectTable(int idx){
        if(validTableName(selectedTable)){
            if(selectedTable != null){
                selectedTable.unselect();
                selectedTable = null;
            }

            if(idx<tables.size()){
                selectedTable = tables.get(idx);
                selectedTable.select();
            }
        }

    }



    /**
     * unselect the currently selected table
     */
    public  void unselectTable(){
        if(validTableName(selectedTable)) {
            if (selectedTable != null) {
                selectedTable.unselect();
                selectedTable = null;
            }
        }
    }



    /**
     * retrieve selected table
     */
    public  Table getSelectedTable(){
        return selectedTable;
    }



    /**
     * edit selected table's name
     */
    public  void editTableName(char keyChar){
        if(selectedTable!=null) {
            selectedTable.editName(keyChar);
        }
    }

    /**
     * restore old name of selected table
     */
    public  void undoNameEdits(){
        if(selectedTable!=null) {
            selectedTable.restoreOldName();
        }
    }

    /**
     * save selected table name and deleted old name
     */
    public  void saveNewName(){
        if(validTableName(selectedTable)) {
            if (selectedTable != null) {
                selectedTable.saveName();
            }
        }
    }


    /**
     * retrieve all tables
     */
    public  ArrayList<Table> getTables(){
        return tables;
    }

    /**
     * create and add a new table
     */
    public  void createAndAddTable(){
        if(validTableName(selectedTable)) {
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

    /**
     * delete selected table
     */
    public  void deleteTable(){
        tables.remove(selectedTable);
        selectedTable = null;
    }


    /**
     * check if table has valid name
     */
    public  boolean validTableName(Table table){
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

    /**
     * check if table name is unique
     */
    public  boolean uniqueTableName(Table table){
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
}
