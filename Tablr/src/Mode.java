import java.awt.Frame;
import java.awt.Graphics;


public abstract class Mode {

    public abstract void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount);
    public abstract void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown);
    public abstract void drawMode(Frame frame, Graphics graphics);

    // ==========================
    // ===      Locating      ===
    // ==========================
    
    //locate table entry in 1D after clicking
    public static int getIdx(int height, int hscale, int width, int wscale,int x, int y, int xoffset, int yoffset) {
        int entryHeight = (height-30) / TableManager.getMaxTablePerCol();
        int entryWidth = width / TableManager.getMaxTablePerRow();

        int entryCol = (x-xoffset)/ entryWidth;
        int entryRow = (y-yoffset)/ entryHeight;
        int entryIdx = (entryRow*wscale)+entryCol;
        return Math.max(0,entryIdx);
    }

    //locate table entry in 2D after clicking
    public static int[] getIdx2D(int height, int hscale, int width, int wscale,int x, int y, int xoffset, int yoffset) {
        int[] position = {0,0};
        int entryHeight = height / hscale;
        int entryWidth = width/wscale;
        int entryRow = (y-yoffset)/ entryHeight;
        int entryCol = (x-xoffset)/ entryWidth;
        position[0] = Math.max(0,entryRow);
        position[1] = Math.max(0,entryCol);
        return position;
    }
}
