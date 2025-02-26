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

    public static boolean hasValidName(Table table){
        if(table == null){
            return true;
        }
        if(table.getName()==""){
            return false;
        }
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

    public static int getMaxTablePerRow(){
        return maxTablePerRow;
    }

    public static int getMaxTablePerCol(){
        return maxTablePerCol;
    }

    //select table with idx
    public static void selectTable(int idx){
        if(hasValidName(getSelectedTable())) {
            if(getSelectedTable()==null) {
                tables.get(idx).select();
            }else {
                getSelectedTable().unselect();
                tables.get(idx).select();
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
    public static void editTableName(Table table,char keyChar){
        String name = table.getName();

        if(keyChar=='\b'){
            table.setName(name.substring(0, name.length()-1));
        }else{
            table.setName(name+keyChar);
        }

    }


    //retrieve all tables
    public static ArrayList<Table> getTables(){
        return tables;
    }

    //create and add a new table
    public static void createAndAddTable(){
        if(hasValidName(getSelectedTable())) {
            if (tables.size() < maxTables) {
                String name = generateName();
                Table table = new Table(name);
                tables.add(table);
            }
        }
    }

    //delete selected table
    public static void deleteTable(Table table){
        tables.remove(table);
    }


}
