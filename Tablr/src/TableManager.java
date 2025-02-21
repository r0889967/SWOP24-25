import java.util.ArrayList;
import java.util.Random;

public class TableManager {
    private static int maxTables = 90;
    private static ArrayList<Table> tables = new ArrayList<Table>();

    private static String generateRandomName(){
        Random random = new Random();
        String name = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        for(int i=0;i<5;i++){
            int idx = random.nextInt(alphabet.length());
            name += alphabet.charAt(idx);
        }
        return "Table"+name;
    }

    public static void selectTable(int idx){
        for(int i=0;i<tables.size();i++){
            if(i==idx){
                tables.get(i).selected = true;
            }else{
                tables.get(i).selected = false;
            }
        }
    }

    public static Table getSelectedTable(){
        for(int i=0;i<tables.size();i++){
            if(tables.get(i).selected){
                return tables.get(i);
            }
        }
        return null;
    }

    public static void editTableName(Table table,char keyChar){
        String name = table.getName();
        if(keyChar=='\b'){
            table.setName(name.substring(0, name.length()-1));
        }else{
            table.setName(name+keyChar);
        }

    }

    public static ArrayList<Table> getTables(){
        return tables;
    }

    public static void createAndAddTable(){
        if(tables.size()<maxTables) {
            String name = generateRandomName();
            String finalName = name;
            while(getTables().stream().anyMatch(t -> t.getName().equals(finalName))){
                name = generateRandomName();
            }
            Table table = new Table(name);
            tables.add(table);
        }
    }

    public static void deleteTable(Table table){
        tables.remove(table);
    }
}
