import java.awt.*;

public class ComponentDrawer {

    //draw list of tables
    public static void drawTableList(Frame frame,Graphics g){
        int width = frame.getWidth();
        int height = frame.getHeight();

        g.setColor(Color.blue);
        g.fillRect(0, 0, width, height/2);

        int entryWidth = width/6;
        int entryHeight = height/30;

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

            g.drawString(name,col*entryWidth,row*entryHeight+entryHeight/2);

            col++;
            if(col%6==0){
                row++;
                col = 0;
            }
        }
    }

    //draw col designer for selected table
    public static void drawTableColDesigner(Frame frame,Graphics g, Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();
    }

    //draw row designer for selected table
    public static void drawTableRowDesigner(Frame frame,Graphics g, Table table){
        int width = frame.getWidth();
        int height = frame.getHeight();
    }


}
