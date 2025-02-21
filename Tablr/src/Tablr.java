import java.util.ArrayList;


public class Tablr {



    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
         new CanvasWindow("Tablr "+ModeManager.getMode()).show();
        });

    }
}