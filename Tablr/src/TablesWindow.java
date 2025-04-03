import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class TablesWindow extends SubWindow {

    public TablesWindow(TableManager tableManager, int xcor, int ycor, int width, int height) {
        super(tableManager, xcor, ycor, width, height);
    }

    /**
     * Handles mouse events
     * Double-clicking: If no table is selected, create a new one
     * Double-clicking: If a table is selected, open the table
     * Opens table design mode when no columns exist yet, otherwise opens table row mode
     * Single-click: Selects the chosen table
     */
    @Override
    public void handleMouseEvent(int id,int x, int y, int clickCount) {
        SubWindowManager.switchSubWindow(x,y, id);
        SubWindow curWindow = SubWindowManager.getWindow();
        if (this.equals(curWindow) && cursorInside(x, y)) {
            if(id==500) {

                Table currentlySelectedTable = tableManager.getSelectedTable();
                int idx = getIdx1D(height - 2 * borderThickness, tableManager.getMaxTablePerCol(), width-2*borderThickness, tableManager.getMaxTablePerRow(), x, y, xcor + borderThickness, ycor + borderThickness);
                if (clickCount == 1) {
                    tableManager.selectTable(idx);
                    tableManager.saveNewName();
                }
                //mouse double clicked
                else if (clickCount == 2) {

                    int rows = (int) Math.ceil((double) tableManager.getTables().size() / tableManager.getMaxTablePerRow());
                    int clickableHeight = ycor+(rows+1) * (height - 30 - 2*borderThickness) / tableManager.getMaxTablePerCol(); // Nog niet volledig correct -> Bij meer dan 1 tabel per rij, kunnen de laatste tabellen niet toegevoegd worden
                    //create new table list entry
                    if (y > clickableHeight) {
                        tableManager.createAndAddTable();
                    }

                    //open table design mode or rows mode for table
                    else if (tableManager.isValidTable(currentlySelectedTable)) {
                        Table selected = tableManager.getSelectedTable();
                        if (selected != null) {

                            //if table has no columns, change to design mode
                            if (selected.getCols().isEmpty()) {
                                SubWindowManager.toTableDesignWindow(tableManager);
                            }

                            //else change to rows mode
                            else {
                                SubWindowManager.toTableRowsWindow(tableManager);
                            }
                        }
                    }
                }
            }else if(id==506){
                dragOrResize(x,y);
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
    public void handleKeyEvent(int id,int keyCode, char keyChar, boolean isControlDown) {
        if (this.equals(SubWindowManager.getWindow())) {
            if (id == 401) {
                //del key
                if (keyCode == 127) {
                    tableManager.deleteTable();
                }

                //escape key
                else if (keyCode == 27) {
                    tableManager.undoNameEdits();
                    tableManager.unselectTable();
                }

                //enter key
                else if (keyCode == 10) {
                    tableManager.saveNewName();
                    tableManager.unselectTable();
                }

                //character keys
                else if (!(keyCode >= 16 && keyCode <= 20)) {
                    tableManager.editTableName(keyChar);
                }
            }
        }
    }

    /**
     * Draws table manager screen
     * If a name is invalid: mark it as red otherwise keep it gray
     */
    @Override
    public void drawMode(Graphics g){
        int height_ = height - 30; // 30 is the height of the Title bar

        int entryWidth = (width-2*borderThickness)/tableManager.getMaxTablePerRow();
        int entryHeight = (height-2*borderThickness)/tableManager.getMaxTablePerCol();
        g.setColor(Color.gray);
        g.fillRect(xcor, ycor, width, height_);

        int row = 0;
        int col = 0;

        for(Table table:tableManager.getTables()) {
            String name = table.getName();

            if(table.equals(tableManager.getSelectedTable())) {
                name += "\uD83D\uDC46";
            }


            if(!tableManager.isValidTable(table)) {
                g.setColor(Color.red);
            }else{
                g.setColor(Color.lightGray);
            }
            g.fillRect(xcor+borderThickness+col*entryWidth,ycor+borderThickness+row*entryHeight,entryWidth,entryHeight);
            g.setColor(Color.darkGray);
            g.drawRect(xcor+borderThickness+col*entryWidth,ycor+borderThickness+row*entryHeight, entryWidth, entryHeight);

            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            g.drawString(name, xcor+borderThickness+col * entryWidth + 10, ycor+borderThickness+row * entryHeight + (entryHeight / 2) + 5);


            col++;
            if(col%tableManager.getMaxTablePerRow()==0){
                row++;
                col = 0;
            }
        }

        drawBorders(g);
        g.setColor(Color.black);
        g.drawString("TablesWindow",xcor,ycor+10);
        drawCloseButton(g);
    }

}