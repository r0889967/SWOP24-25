import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class TablesMode extends Mode {

    public TablesMode(TableManager tableManager){
        super(tableManager);
    }

    /**
     * Handles mouse events
     * Double-clicking: If no table is selected, create a new one
     * Double-clicking: If a table is selected, open the table
     * Opens table design mode when no columns exist yet, otherwise opens table row mode
     * Single-click: Selects the chosen table
     */
    @Override
    public void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount) {
        Table currentlySelectedTable = tableManager.getSelectedTable();
        int idx = getIdx1D(frame.getHeight() - 30,tableManager.getMaxTablePerCol(),frame.getWidth(),tableManager.getMaxTablePerRow(),x,y,0,0);
        if(clickCount==1){
            tableManager.selectTable(idx);
            tableManager.saveNewName();
        }
        //mouse double clicked
        else if(clickCount==2){
            
            int rows = (int) Math.ceil((double) tableManager.getTables().size() / tableManager.getMaxTablePerRow());
            int clickableHeight = rows * (frame.getHeight() - 30) / tableManager.getMaxTablePerCol(); // Nog niet volledig correct -> Bij meer dan 1 tabel per rij, kunnen de laatste tabellen niet toegevoegd worden
            //create new table list entry
            if(y>clickableHeight){
                tableManager.createAndAddTable();
            }

            //open table design mode or rows mode for table
            else if (tableManager.validTableName(currentlySelectedTable)) {
                Table selected = tableManager.getSelectedTable();
                if(selected!=null) {

                    //if table has no columns, change to design mode
                    if (selected.getCols().isEmpty()) {
                        ModeManager.toTableDesignMode(tableManager);
                        window.setTitle(CONST_TABLE_COLUMN_MODE_TITLE + " - " + selected.getName());
                    }

                    //else change to rows mode
                    else {
                        ModeManager.toTableRowsMode(tableManager);
                        window.setTitle(CONST_TABLE_ROW_MODE_TITLE + " - " + selected.getName());
                    }
                }
            }
        }
    }

    /**
     * Handles keyboard events
     * Delete: if a table is selected then delete the table otherwise do nothing
     * When editing a table name:
     * Escape: stop editing the name and undo edits that haven't been saved
     * Enter: stop editing the name and save changes
     * Backspace: deletes last character of name (if not empty)
     * Any non-special character: appends the character to the name
     */
    @Override
    public void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown) {
        //del key
        if (keyCode == 127) {
            tableManager.deleteTable();
        }

        //escape key
        else if(keyCode==27){
            tableManager.undoNameEdits();
            tableManager.unselectTable();
        }

        //enter key
        else if(keyCode==10){
            tableManager.saveNewName();
            tableManager.unselectTable();
        }

        //character keys
        else if(!(keyCode>=16 && keyCode<=20)) {
            tableManager.editTableName(keyChar);
        }
    }

    /**
     * Draws table manager screen
     * If a name is invalid: mark it as red otherwise keep it gray
     */
    @Override
    public void drawMode(Frame frame, Graphics g){
        int width = frame.getWidth();
        int height = frame.getHeight() - 30; // 30 is the height of the Title bar

        int entryWidth = width/tableManager.getMaxTablePerRow();
        int entryHeight = height/tableManager.getMaxTablePerCol();
        g.setColor(Color.gray);
        g.fillRect(0, 0, width, height);

        int row = 0;
        int col = 0;

        for(Table table:tableManager.getTables()) {
            String name = table.getName();
                      
            if(table.isSelected()){
                name += "\uD83D\uDC46";
            }


            if(!tableManager.validTableName(table)) {
                g.setColor(Color.red);
            }else{
                g.setColor(Color.lightGray);
            }
            g.fillRect(col*entryWidth,row*entryHeight,entryWidth,entryHeight);
            g.setColor(Color.darkGray);
            g.drawRect(col*entryWidth,row*entryHeight, entryWidth, entryHeight);

            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            g.drawString(name, col * entryWidth + 10, row * entryHeight + (entryHeight / 2) + 5);


            col++;
            if(col%tableManager.getMaxTablePerRow()==0){
                row++;
                col = 0;
            }
        }
    }

}