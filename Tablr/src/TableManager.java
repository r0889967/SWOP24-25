import java.util.ArrayList;
import java.util.Random;

public class TableManager {
    private static int maxTables = 90;
    private static ArrayList<Table> tables = new ArrayList<Table>();
    private static int sequenceNumber = 0;

    //generate random name for table
    private static String generateName(){
        sequenceNumber++;
        return "Table"+sequenceNumber;
    }

    //select table with idx
    public static void selectTable(int idx){
        for(int i=0;i<tables.size();i++){
            if(i==idx){
                tables.get(i).select();
            }else{
                tables.get(i).unselect();
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
        if(tables.size()<maxTables) {
            String name = generateName();
            Table table = new Table(name);
            tables.add(table);
        }
    }

    //delete selected table
    public static void deleteTable(Table table){
        tables.remove(table);
    }
}
