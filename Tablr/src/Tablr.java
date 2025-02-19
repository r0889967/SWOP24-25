import java.util.ArrayList;
import java.util.Random;

public class Tablr {

    static ArrayList<Table> tables = new ArrayList<Table>();
    static String mode = "Tables mode";
    static int maxTables = 60;



    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
         new CanvasWindow("My Canvas Window "+mode).show();
        });

    }
}