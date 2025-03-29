import java.util.ArrayList;

public class TableManager {
    private final int maxTablePerRow = 1;
    private final int maxTablePerCol = 20;
    private final ArrayList<Table> tables = new ArrayList<>();
    private int sequenceNumber = 1;
    private Table selectedTable = null;

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
        if(isValidTable(selectedTable) && idx >= 0 && idx < tables.size()) {
            selectedTable = tables.get(idx);
        }
    }

    /**
     * unselect the currently selected table
     */
    public  void unselectTable(){
        if(isValidTable(selectedTable)) {
            selectedTable = null;
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
        if(isValidTable(selectedTable)) {
            if(selectedTable!=null) {
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
        if(isValidTable(selectedTable) && tables.size() < maxTablePerRow * maxTablePerCol) {
            String name = generateName();
            Table newTable = new Table(name);
            while(!isValidTable(newTable)) {
                name = generateName();
                newTable.setBothNames(name);
            }
            tables.add(newTable);
        }
    }

    /**
     * delete selected table
     */
    public  void deleteTable(){
        if (selectedTable != null) {
            tables.remove(selectedTable);
            selectedTable = null;
        }
    }


    /**
     * check if table has valid name
     */
    public boolean isValidTable(Table table){
        if(table == null){
            return true;
        }
        return !table.getName().isEmpty() && uniqueTableName(table);
    }

    /**
     * check if table name is unique
     */
    private boolean uniqueTableName(Table table){
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
