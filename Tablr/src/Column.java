import java.util.Random;

public class Column {

    private String name;
    private String type = "string";
    private boolean allowsBlanks = true;
    private String defaultValue = "";

    Column(String name) {
        this.name = name;
    }

    //generate random name for table
    private static String generateRandomName(){
        Random random = new Random();
        String name = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

        for(int i=0;i<3;i++){
            int idx = random.nextInt(alphabet.length());
            name += alphabet.charAt(idx);
        }

        return "Column"+name;
    }

    public void render(){

    }

}
