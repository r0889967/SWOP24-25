import java.awt.*;
import java.util.ArrayList;

public class ComponentDrawer {

    //draw list of tables
    public static void drawTableList(Frame frame,Graphics g){
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


            if(!TableManager.hasValidName(table)) {
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

    //draw col designer for selected table
    public static void drawTableColDesigner(Frame frame,Graphics g, Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height/8);

        ArrayList<Column> cols = table.getCols();

        if(!cols.isEmpty()) {

            int entryWidth = width / cols.size();
            int entryHeight = height / 8;


            int i = 0;
            for (Column col : cols) {

                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

                String name = cols.get(i).getName();
                String type = cols.get(i).getType();
                String allowsBlanks = String.valueOf(cols.get(i).allowsBlanks());
                String defaultValue = cols.get(i).getDefaultValue();
                if(col.isSelected()){
                    if(ColumnManager.getEditMode()==0) {
                        name += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==1) {
                        type += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==2) {
                        allowsBlanks += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==3) {
                        defaultValue += "\uD83D\uDC46";
                    }
                }

                if(!ColumnManager.hasValidName(col)) {
                    g.setColor(Color.red);
                }else{
                    g.setColor(Color.lightGray);
                }
                g.fillRect(i * entryWidth, 0, entryWidth, entryHeight);

                g.setColor(Color.black);
                g.drawString(name, i * entryWidth, 10);
                g.drawString(type, i * entryWidth, height/32+10);
                g.drawString("Blanks?"+allowsBlanks, i * entryWidth, height*2/32+10);
                g.drawString("DVal:"+defaultValue, i * entryWidth, height*3/32+10);
                i++;

            }
        }


    }

    //draw row designer for selected table
    public static void drawTableRowDesigner(Frame frame,Graphics g, Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height/8);

        ArrayList<Column> cols = table.getCols();

        if(!cols.isEmpty()) {

            int entryWidth = width / cols.size();
            int entryHeight = height / 8;


            int i = 0;
            for (Column col : cols) {

                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

                String name = cols.get(i).getName();
                String type = cols.get(i).getType();
                String allowsBlanks = String.valueOf(cols.get(i).allowsBlanks());
                String defaultValue = cols.get(i).getDefaultValue();
                if(col.isSelected()){
                    if(ColumnManager.getEditMode()==0) {
                        name += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==1) {
                        type += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==2) {
                        allowsBlanks += "\uD83D\uDC46";
                    }else if(ColumnManager.getEditMode()==3) {
                        defaultValue += "\uD83D\uDC46";
                    }
                }

                if(!ColumnManager.hasValidName(col)) {
                    g.setColor(Color.red);
                }else{
                    g.setColor(Color.lightGray);
                }
                g.fillRect(i * entryWidth, 0, entryWidth, entryHeight);

                g.setColor(Color.black);
                g.drawString(name, i * entryWidth, 10);
                g.drawString(type, i * entryWidth, height/32+10);
                g.drawString("Blanks?"+allowsBlanks, i * entryWidth, height*2/32+10);
                g.drawString("DVal:"+defaultValue, i * entryWidth, height*3/32+10);
                i++;

            }
        }
    }


}
