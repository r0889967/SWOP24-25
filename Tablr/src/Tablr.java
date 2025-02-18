import java.util.ArrayList;

public class Tablr {

    static ArrayList<Table> tables = new ArrayList<Table>();
    static String mode = "Tables mode";

    public static void changeMode(String mode) {
        Tablr.mode = mode;
    }


    public static void main(String[] args) {

        tables.add(new Table(mode));







        java.awt.EventQueue.invokeLater(() -> {
         new CanvasWindow("My Canvas Window "+mode).show();
        });





    }
}