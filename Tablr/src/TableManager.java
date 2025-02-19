import java.util.ArrayList;
import java.util.Random;

public class TableManager {

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

    public static ArrayList<Table> getTables(){
        return Tablr.tables;
    }

    public static void createAndAddTable(){
        if(Tablr.tables.size()<Tablr.maxTables) {
            String name = generateRandomName();
            String finalName = name;
            while(getTables().stream().anyMatch(t -> t.name.equals(finalName))){
                name = generateRandomName();
            }
            Table table = new Table(name);
            Tablr.tables.add(table);
        }
    }

    public static void deleteTable(String name){}
}
