import java.util.ArrayList;


public class Tablr {


    static String mode = "Tables mode";



    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
         new CanvasWindow("My Canvas Window "+mode).show();
        });

    }
}