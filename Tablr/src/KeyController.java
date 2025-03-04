public class KeyController {

    private static void handle1(int keyCode,char keyChar) {
        //del key
        if (keyCode == 127) {
            TableManager.deleteTable();
        }

        //escape key
        else if(keyCode==27){
            TableManager.undoEditName();
            TableManager.unselectTable();
        }

        //enter key
        else if(keyCode==10){
            TableManager.saveNewName();
            TableManager.unselectTable();
        }

        //character keys
        else if(!(keyCode>=16 && keyCode<=20)) {
            TableManager.editTableName(keyChar);
        }

    }

    private static void handle2(CanvasWindow window,int keyCode,char keyChar){
        //escape key
        if (keyCode == 27) {
            window.setTitle("Tablr " + ModeManager.toTablesMode());
        }

        //enter
        else if (keyCode == 10) {
            ColumnManager.unselectCol();
        }

        //crtl
        else if (keyCode == 17) {
            window.setTitle("Tablr " + ModeManager.toTableRowsMode());
        }

        //del key
        else if (keyCode == 127) {
            ColumnManager.deleteCol();
        }

        //character keys
        else if (!((keyCode >= 16) && (keyCode <= 20))) {
            ColumnManager.editColAttributes(keyChar);
        }

    }

    private static void handle3(CanvasWindow window,int keyCode,char keyChar){
        //escape key
        if (keyCode == 27) {
            window.setTitle("Tablr " + ModeManager.toTablesMode());
        }

        //enter
        else if(keyCode == 10) {
            RowManager.unselectCell();
            RowManager.unselectRow();
        }

        //crtl
        else if (keyCode == 17) {
            window.setTitle("Tablr " + ModeManager.toTableDesignMode());
        }

        //del key
        else if (keyCode == 127) {
            RowManager.deleteRow();
        }

        else if (!((keyCode >= 16) && (keyCode <= 20))) {
            RowManager.editCellValue(keyChar);
        }

    }

    public static void handle(CanvasWindow window,int keyCode,char keyChar){
        //tables mode
        if (ModeManager.getMode().equals("Tables mode")) {
            KeyController.handle1(keyCode, keyChar);
        }

        //table design mode
        else if (ModeManager.getMode().equals("Table design mode")) {
            KeyController.handle2(window, keyCode, keyChar);
        }

        //table row mode
        else {
            KeyController.handle3(window, keyCode, keyChar);
        }
    }
}
