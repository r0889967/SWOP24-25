package old;
import java.awt.Frame;

public class MouseController {


    private static void handle1(Frame frame, CanvasWindow window,int x, int y, int clickCount) {
        //mouse clicked, select table list entry
        if(clickCount==1){
            TableManager.saveNewName();
            int idx = Locator.getIdx(frame.getHeight(),30,frame.getWidth(),TableManager.getMaxTablePerRow(),x,y,0,0);
            TableManager.selectTable(idx);
        }
        //mouse double clicked
        else if(clickCount==2){

            //create new table list entry
            if(y>30*TableManager.getMaxTablePerCol()){
                TableManager.createAndAddTable();
            }

            //open table design mode or rows mode for table
            else{
                Table selected = TableManager.getSelectedTable();
                if(selected!=null) {

                    //if table has no columns, change to design mode
                    if (selected.getCols().isEmpty()) {
                        window.setTitle("Tablr " + ModeManager.toTableDesignMode());
                    }

                    //else change to rows mode
                    else {
                        window.setTitle("Tablr " + ModeManager.toTableRowsMode());
                    }
                }
            }
        }

    }

    private static void handle2(Frame frame, int x, int y, int clickCount) {
        //mouse clicked, select col of table
        if(clickCount==1){
            Table selectedTable = TableManager.getSelectedTable();
            if (selectedTable != null){
                int idx = Locator.getIdx(frame.getHeight(),8,frame.getWidth(),selectedTable.getCols().size(),x,y,0,0);
                selectedTable.selectCol(idx);
                int idx2 = Locator.getIdx(frame.getHeight()/8,4,frame.getWidth(),1,x,y,0,0);
                selectedTable.setColumnEditMode(idx2);
                selectedTable.editColAttributes('\0');
            }
        }
        //mouse is double-clicked outside column list, add col to table
        else if (y > 7*frame.getHeight()/8 && clickCount == 2) {
            Table selected = TableManager.getSelectedTable();
            if (selected != null){
                selected.addCol();
            }
        }
    }

    private static void handle3(Frame frame, int x, int y, int clickCount) {
        //mouse clicked, select row and cell of table
        if(clickCount==1){
            Table selectedTable = TableManager.getSelectedTable();
            int idx = Locator.getIdx(3*frame.getHeight()/4,selectedTable.getRows().size(),frame.getWidth(),1,x,y,x,frame.getHeight()/8);
            selectedTable.selectRow(idx);

            int[] position = Locator.getIdx2D(3*frame.getHeight()/4,selectedTable.getRows().size(),frame.getWidth(),selectedTable.getCols().size(),x,y,0,frame.getHeight()/8);
            selectedTable.selectCell(position[0],position[1]);
        }

        //mouse is double-clicked outside row list, add row to table
        else if(y>7*frame.getHeight()/8 && clickCount==2) {
            Table selectedTable = TableManager.getSelectedTable();
            if (selectedTable != null){
                selectedTable.addRow();
            }
        }

    }

    public static void handle(Frame frame, CanvasWindow window,int x, int y, int clickCount) {
        //tables mode
        if(ModeManager.getMode().equals("Tables mode")) {
            MouseController.handle1(frame,window,x,y,clickCount);
        }
        //table design mode
        else if (ModeManager.getMode().equals("Table design mode")) {
            MouseController.handle2(frame,x,y,clickCount);
        }
        //table rows mode
        else{
            MouseController.handle3(frame,x,y,clickCount);
        }

    }



}
