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


            g.setColor(Color.lightGray);
            g.fillRect(col*entryWidth,row*entryHeight,entryWidth,entryHeight);
            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            String name = table.getName();


            if(table.isSelected()){
                name+="<<";
            }

            if(!TableManager.hasValidName(table)) {
                g.setColor(Color.red);
                g.fillRect(col*entryWidth,row*entryHeight,entryWidth,entryHeight);
            }

            g.setColor(Color.black);
            g.drawString(name,col*entryWidth,row*entryHeight+entryHeight/2);

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
        g.fillRect(0, 0, width, height/4);

        ArrayList<Column> cols = table.getCols();

        if(!cols.isEmpty()) {

            int entryWidth = width / cols.size();
            int entryHeight = height / 8;


            int i = 0;
            for (Column col : cols) {

                g.setColor(Color.lightGray);
                g.fillRect(i * entryWidth, 0, entryWidth, entryHeight);
                g.setColor(Color.black);
                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

                String name = cols.get(i).getName();
                if(col.isSelected()){
                    name+="<<";
                }


                g.drawString(name, i * entryWidth, 10);
                g.drawString(col.getType(), i * entryWidth, 20);
                g.drawString(String.valueOf(col.allowsBlanks()), i * entryWidth, 30);
                g.drawString(col.getDefaultValue(), i * entryWidth, 40);
                i++;

            }
        }


    }

    //draw row designer for selected table
    public static void drawTableRowDesigner(Frame frame,Graphics g, Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();
    }


}
