import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class TablesMode extends Mode {

    public TablesMode(){
        super();
    }

    @Override
    public void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount) {
        if(clickCount==1){
            TableManager.saveNewName();
            int idx = getIdx(frame.getHeight(),30,frame.getWidth(),TableManager.getMaxTablePerRow(),x,y,0,0);
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
                        window.setTitle("Tablr: " + ModeManager.toTableDesignMode());
                    }

                    //else change to rows mode
                    else {
                        window.setTitle("Tablr: " + ModeManager.toTableRowsMode());
                    }
                }
            }
        }
    }
    
    @Override
    public void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown) {
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

    @Override
    public void drawMode(Frame frame, Graphics g){
        int width = frame.getWidth();
        int height = frame.getHeight();

        int entryWidth = width/TableManager.getMaxTablePerRow();
        int entryHeight = height/30;
        g.setColor(Color.blue);
        g.fillRect(0, 0, width, entryHeight*TableManager.getMaxTablePerCol());

        int row = 0;
        int col = 0;

        for(Table table:TableManager.getTables()) {

            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            String name = table.getName();

            if(table.isSelected()){
                name+="\uD83D\uDC46";
            }


            if(!TableManager.validTableName(table)) {
                g.setColor(Color.red);
            }else{
                g.setColor(Color.lightGray);
            }
            g.fillRect(col*entryWidth,row*entryHeight,entryWidth,entryHeight);

            g.setColor(Color.black);
            g.drawString(name, col * entryWidth, row * entryHeight + entryHeight / 2);


            col++;
            if(col%TableManager.getMaxTablePerRow()==0){
                row++;
                col = 0;
            }
        }
    }

}