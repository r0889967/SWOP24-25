import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;

public class TableDesignMode extends Mode {
    
    public TableDesignMode() {
        super();
    }
    

    @Override
    public void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount) {
        //mouse clicked, select col of table
        if(clickCount==1){
            Table selectedTable = TableManager.getSelectedTable();
            if (selectedTable != null){
                int idx = getIdx(frame.getHeight(),8,frame.getWidth(),selectedTable.getCols().size(),x,y,0,0);
                selectedTable.selectCol(idx);
                int idx2 = getIdx(frame.getHeight()/8,4,frame.getWidth(),1,x,y,0,0);
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
    
    @Override
    public void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown) {
        Table selectedTable = TableManager.getSelectedTable();
        if (selectedTable != null) {
            //escape key
            if (keyCode == 27) {
                window.setTitle("Tablr: " + ModeManager.toTablesMode());
            }

            //enter
            else if (keyCode == 10) {
                if (isControlDown) {
                    // Ctrl+Enter switches to table rows mode
                    window.setTitle("Tablr: " + ModeManager.toTableRowsMode());
                } else {
                    // Just Enter unselects column
                    selectedTable.unselectCol();
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
    
    @Override
    public void drawMode(Frame frame, Graphics g){
        Table table = TableManager.getSelectedTable();
        drawRows(frame, g, table);
        drawCols(frame, g, table);
    }

    private static void drawCols(Frame frame,Graphics g,Table table){
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
                if (col.isSelected()) {
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
                if(!table.validColName(col,cols)){
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

    private static void drawRows(Frame frame,Graphics g,Table table){
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
                    if(cell.isSelected()){
                        value+="\uD83D\uDC46";
                    }
                    g.drawString(value, col * cellEntryWidth, (height / 8) +row * cellEntryHeight+10);
                    col++;
                }
                col = 0;
                if(r.isSelected()){
                    g.drawString("\uD83D\uDC46",0,height/8+(row+1)*cellEntryHeight);
                }
                row++;
            }
        }

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height / 8);
    }
}
