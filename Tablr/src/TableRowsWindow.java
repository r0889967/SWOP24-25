import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

public class TableRowsWindow extends SubWindow {

    private Table selectedTable;

    public TableRowsWindow(TableManager tableManager, int xcor, int ycor, int width, int height) {
        super(tableManager, xcor, ycor, width, height);
        selectedTable = tableManager.getSelectedTable();
    }

    /**
     * Handles mouse events
     * Double-clicking: If at the bottom of the screen: create a new row with default values
     * Single-click: Selects the clicked row and cell and marks the cell for editing
     */
    @Override
    public void handleMouseEvent(int id,int x, int y, int clickCount) {
        SubWindowManager.switchSubWindow(x,y, id);
        SubWindow curWindow = SubWindowManager.getWindow();
        if (this.equals(curWindow) && cursorInside(x, y)) {
            if (id == 500) {

                //mouse clicked, select row and cell of table
                if (clickCount == 1) {

                    if(cursorInsideCloseButton(x, y)) {
                        SubWindowManager.removeSubWindow(this);
                    }

                    selectedTable.unSelectCell();
                    selectedTable.unSelectRow();

                    int idx = getIdx1D(3 * height / 4, selectedTable.getRows().size(), width, 1, x, y, xcor + x, ycor + height / 8);
                    int[] position = getIdx2D(3 * height / 4, selectedTable.getRows().size(), width, selectedTable.getCols().size(), x, y, xcor, ycor + height / 8);
                    selectedTable.selectRow(idx);
                    selectedTable.selectCell(position[0], position[1]);

                }

                //mouse is double-clicked outside row list, add row to table
                else if (y > 7 * height / 8 && clickCount == 2) {
                    if (selectedTable != null) {
                        selectedTable.addRow();
                    }
                }
            }else if(id==506){
                dragOrResize(x,y);
            }

        }


    }

    /**
     * Handles keyboard events
     * Delete: The selected row is deleted. If no row is selected do nothing
     * Escape: Return to tables mode. Only allowed if all cells are in a valid state.
     * Ctrl + Enter: Switches to table designer mode if all columns are in a valid state
     * When editing a cell value:
     * Enter: stop editing value
     * Backspace: deletes last character of value (if not empty)
     * Any non-special character: appends the character to the name
     * If column has type integer: Any non-special non-numeric character is rejected
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, boolean isControlDown) {
        if (this.equals(SubWindowManager.getWindow())) {

            if (id == 401) {
                if (selectedTable != null) {
                    //escape key
                    if (keyCode == 27) {

                    }

                    //enter
                    else if (keyCode == 10) {
                        if (selectedTable.allValidColumns()) {
                            if (isControlDown) {
                                // Ctrl+Enter switches to table rows mode
                                SubWindowManager.openTableDesignWindow(tableManager);
                            } else {
                                // Just Enter unselects column
                                selectedTable.unSelectCell();
                                selectedTable.unSelectRow();
                            }
                        }
                    }

                    //del key
                    else if (keyCode == 127) {
                        selectedTable.deleteRow();
                    }

                    //other keys
                    else if (!((keyCode >= 16) && (keyCode <= 20))) {
                        selectedTable.editCellValue(keyChar);
                    }
                }
            }
        }
    }

    /**
     * Draws table designer screen
     * If a name or default value is invalid: mark it as red otherwise keep it gray
     * If a cell in the appropriate column has an invalid value:
     * Mark the violated field in red otherwise keep it gray
     */
    @Override
    public void drawMode(Graphics g){
        drawRows(g, selectedTable);
        drawCols(g, selectedTable);
        drawBorders(g);
        g.setColor(Color.black);
        g.drawString("Rows mode of "+selectedTable.getName(),xcor,ycor+10);
        drawCloseButton(g);
    }

    private void drawCols(Graphics g,Table table){
        ArrayList<Column> cols = table.getCols();

        g.setColor(Color.CYAN);
        g.fillRect(xcor, ycor, width, height / 8);

        if (!cols.isEmpty()) {

            int colEntryWidth = (width-2*borderThickness) / cols.size();
            int colEntryHeight = (height-2*borderThickness) / 8;


            int i = 0;
            for (Column col : cols) {

                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

                String name = cols.get(i).getName();
                String type = cols.get(i).getType();
                String allowsBlanks = cols.get(i).allowsBlanks() ? "☑" : "☐";
                String defaultValue = cols.get(i).getDefaultValue();
                if (col.equals(table.getSelectedCol())) {
                    switch (table.getColumnEditMode()) {
                        case 0:
                            name += "\uD83D\uDC46";
                            break;
                        case 1:
                            type += "\uD83D\uDC46";
                            break;
                        case 2:
                            allowsBlanks += "\uD83D\uDC46";
                            break;
                        case 3:
                            defaultValue += "\uD83D\uDC46";
                            break;
                        default:
                            break;
                    }
                }


                g.setColor(Color.lightGray);
                g.fillRect(xcor+borderThickness+i * colEntryWidth, ycor+borderThickness, colEntryWidth, colEntryHeight);
                g.setColor(Color.red);
                if(!table.validColName(col)){
                    g.fillRect(xcor+borderThickness+i * colEntryWidth, ycor+borderThickness, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColType(col)){
                    g.fillRect(xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+colEntryHeight/4, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColAllowBlanks(col)){
                    g.fillRect(xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+colEntryHeight/2, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColDefaultValue(col)){
                    g.fillRect(xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+3*colEntryHeight/4, colEntryWidth, colEntryHeight/4);
                }

                g.setColor(Color.black);
                g.drawString(name, xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+10);
                g.drawString(type, xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+height / 32 + 10);
                g.drawString("Blanks?" + allowsBlanks, xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+height * 2 / 32 + 10);
                g.drawString("DVal:" + defaultValue, xcor+borderThickness+i * colEntryWidth, ycor+borderThickness+height * 3 / 32 + 10);


                g.drawLine(xcor+borderThickness+i*colEntryWidth, ycor+borderThickness, xcor+borderThickness+i*colEntryWidth, ycor+borderThickness+7*height/8);
                i++;
            }
        }

    }

    private void drawRows(Graphics g,Table table){
        ArrayList<Column> cols = table.getCols();
        ArrayList<Row> rows = table.getRows();

        g.setColor(Color.green);
        g.fillRect(xcor, ycor+height / 8, width, (3 * height) / 4);

        if (!cols.isEmpty() && !rows.isEmpty()) {
            int cellEntryWidth = (width-2*borderThickness) / cols.size();
            int cellEntryHeight = ((3*height-2*borderThickness) / 4)/rows.size();

            int row = 0;
            int col = 0;
            for (Row r : rows) {
                for (Cell cell : r.getCells()) {
                    if(row%2==0) {
                        g.setColor(new Color(237, 237, 237));
                    }else{
                        g.setColor(new Color(200, 200, 200));
                    }
                    g.fillRect(xcor+borderThickness+col * cellEntryWidth, ycor+borderThickness+(height / 8) + row * cellEntryHeight, cellEntryWidth, cellEntryHeight);
                    g.setColor(Color.black);

                    String value = cell.getValue();
                    if(cell.equals(table.getSelectedCell())){
                        value+="\uD83D\uDC46";
                    }
                    g.drawString(value, xcor+borderThickness+col * cellEntryWidth, ycor+borderThickness+(height / 8) +row * cellEntryHeight+10);
                    col++;
                }
                col = 0;
                if(r.equals(table.getSelectedRow())){
                    g.drawString("\uD83D\uDC46",xcor+borderThickness,ycor+borderThickness+height/8+(row+1)*cellEntryHeight);
                }
                row++;
            }
        }
    }
}