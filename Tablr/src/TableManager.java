import java.util.ArrayList;
import java.util.Random;

public class TableManager {
    private static int maxTables = 60;
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

    public static void selectTable(){

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
