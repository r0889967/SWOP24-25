import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;

public class TableDesignWindow extends SubWindow {
    
    public TableDesignWindow(TableManager tableManager) {
        super(tableManager);
    }

    /**
     * Handles mouse events
     * Double-clicking: If at the bottom of the screen: create a new column otherwise do nothing
     * Single-click: Selects the clicked column and marks the selected field for editing.
     * If the currently selected column is in an invalid state the select request is rejected
     * Clicking below the fields results in the last one being selected (if possible)
     */
    @Override
    public void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount) {
        //mouse clicked, select col of table
        if(clickCount==1){
            Table selectedTable = tableManager.getSelectedTable();
            if (selectedTable != null){
                int idx = getIdx1D(frame.getHeight(),8,frame.getWidth(),selectedTable.getCols().size(),x,y,0,0);
                selectedTable.selectCol(idx);
                int idx2 = getIdx1D(frame.getHeight()/8,4,frame.getWidth(),1,x,y,0,0);
                selectedTable.setColumnEditMode(idx2);
                selectedTable.editColAttributes('\0');
            }
        }
        //mouse is double-clicked outside column list, add col to table
        else if (y > 7*frame.getHeight()/8 && clickCount == 2) {
            Table selected = tableManager.getSelectedTable();
            if (selected != null){
                selected.addCol();
            }
        }
    }

    /**
     * Handles keyboard events
     * Delete: The selected column is deleted. If no column is selected do nothing
     * Escape: Return to tables mode. Only allowed if all columns are in a valid state.
     * Ctrl + Enter: Switches to table rows mode if all columns are in a valid state
     * When editing a column name or the default value of string/email:
     * Enter: stop editing the name
     * Backspace: deletes last character of name (if not empty)
     * Any non-special character: appends the character to the name
     * When editing a column type, allow blanks value or boolean/integer default value
     * Any character: same behaviour as clicking it = switches to next option
     */
    @Override
    public void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown) {
        Table selectedTable = tableManager.getSelectedTable();
        if (selectedTable != null) {
            //escape key
            if (keyCode == 27) {
                if (selectedTable.allValidColumns()) {
                    SubWindowManager.toTablesWindow(tableManager);
                    window.setTitle(CONST_TABLE_MODE_TITLE);
                }
            }

            //enter
            else if (keyCode == 10) {
                if (selectedTable.allValidColumns()){
                    if (isControlDown) {
                        // Ctrl+Enter switches to table rows mode
                        // Do not switch if there are no columns
                        if (!selectedTable.getCols().isEmpty()){
                            SubWindowManager.toTableRowsWindow(tableManager);
                            window.setTitle(CONST_TABLE_ROW_MODE_TITLE + " - " + selectedTable.getName());
                        }
                    } else {
                        // Just Enter unselects column
                        selectedTable.unselectCol();
                    }
                }
            }

            //del key
            else if (keyCode == 127) {
                selectedTable.deleteCol();
            }

            //character keys
            else if (!((keyCode >= 16) && (keyCode <= 20))) {
                selectedTable.editColAttributes(keyChar);
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
    public void drawMode(Frame frame, Graphics g){
        Table table = tableManager.getSelectedTable();
        if (table != null){
            drawRows(frame, g, table);
            drawCols(frame, g, table);
        }
    }

    private void drawCols(Frame frame,Graphics g,Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();
        ArrayList<Column> cols = table.getCols();

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height / 8);

        if (!cols.isEmpty()) {

            int colEntryWidth = width / cols.size();
            int colEntryHeight = height / 8;


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
                g.fillRect(i * colEntryWidth, 0, colEntryWidth, colEntryHeight);
                g.setColor(Color.red);
                if(!table.validColName(col)){
                    g.fillRect(i * colEntryWidth, 0, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColType(col)){
                    g.fillRect(i * colEntryWidth, colEntryHeight/4, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColAllowBlanks(col)){
                    g.fillRect(i * colEntryWidth, colEntryHeight/2, colEntryWidth, colEntryHeight/4);
                }
                if(!table.validColDefaultValue(col)){
                    g.fillRect(i * colEntryWidth, 3*colEntryHeight/4, colEntryWidth, colEntryHeight/4);
                }

                g.setColor(Color.black);
                g.drawString(name, i * colEntryWidth, 10);
                g.drawString(type, i * colEntryWidth, height / 32 + 10);
                g.drawString("Blanks?" + allowsBlanks, i * colEntryWidth, height * 2 / 32 + 10);
                g.drawString("DVal:" + defaultValue, i * colEntryWidth, height * 3 / 32 + 10);


                g.drawLine(i*colEntryWidth, 0, i*colEntryWidth, 7*height/8);
                i++;
            }
        }

    }

    private void drawRows(Frame frame,Graphics g,Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();
        ArrayList<Column> cols = table.getCols();
        ArrayList<Row> rows = table.getRows();

        g.setColor(Color.green);
        g.fillRect(0, height / 8, width, 3 * height / 4);

        if (!cols.isEmpty() && !rows.isEmpty()) {
            int cellEntryWidth = width / cols.size();
            int cellEntryHeight = (3*height / 4)/rows.size();

            int row = 0;
            int col = 0;
            for (Row r : rows) {
                for (Cell cell : r.getCells()) {
                    if(row%2==0) {
                        g.setColor(new Color(237, 237, 237));
                    }else{
                        g.setColor(new Color(200, 200, 200));
                    }
                    g.fillRect(col * cellEntryWidth, (height / 8) + row * cellEntryHeight, cellEntryWidth, cellEntryHeight);
                    g.setColor(Color.black);

                    String value = cell.getValue();
                    if(cell.equals(table.getSelectedCell())){
                        value+="\uD83D\uDC46";
                    }
                    g.drawString(value, col * cellEntryWidth, (height / 8) +row * cellEntryHeight+10);
                    col++;
                }
                col = 0;
                if(r.equals(table.getSelectedRow())){
                    g.drawString("\uD83D\uDC46",0,height/8+(row+1)*cellEntryHeight);
                }
                row++;
            }
        }

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height / 8);
    }
}
