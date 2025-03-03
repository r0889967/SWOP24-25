import java.awt.*;
import java.util.ArrayList;

public class ComponentDrawer {

    //draw list of tables
    private static void drawTableList(Frame frame,Graphics g){
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


            if(!ErrorChecker.validTableName(table,TableManager.getTables())) {
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
                String allowsBlanks = String.valueOf(cols.get(i).allowsBlanks());
                String defaultValue = cols.get(i).getDefaultValue();
                if (col.isSelected()) {
                    if (ColumnManager.getEditMode() == 0) {
                        name += "\uD83D\uDC46";
                    } else if (ColumnManager.getEditMode() == 1) {
                        type += "\uD83D\uDC46";
                    } else if (ColumnManager.getEditMode() == 2) {
                        allowsBlanks += "\uD83D\uDC46";
                    } else if (ColumnManager.getEditMode() == 3) {
                        defaultValue += "\uD83D\uDC46";
                    }
                }


                g.setColor(Color.lightGray);
                g.fillRect(i * colEntryWidth, 0, colEntryWidth, colEntryHeight);
                g.setColor(Color.red);
                if(!ErrorChecker.validColName(col,cols)){
                    g.fillRect(i * colEntryWidth, 0, colEntryWidth, colEntryHeight/4);
                }
                if(!ErrorChecker.validColType(col)){
                    g.fillRect(i * colEntryWidth, colEntryHeight/4, colEntryWidth, colEntryHeight/4);
                }
                if(!ErrorChecker.validColAllowBlanks(col)){
                    g.fillRect(i * colEntryWidth, colEntryHeight/2, colEntryWidth, colEntryHeight/4);
                }
                if(!ErrorChecker.validColDefaultValue(col)){
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


    //draw editor for selected table
    private static void drawTableEditor(Frame frame,Graphics g, Table table) {
        drawRows(frame, g, table);
        drawCols(frame, g, table);
    }

    public static void draw(Frame frame,Graphics g){
        Table selected = TableManager.getSelectedTable();
        if(ModeManager.getMode().equals("Tables mode")) {
            drawTableList(frame, g);
        }else{
            drawTableEditor(frame,g,selected);
        }
    }

}
