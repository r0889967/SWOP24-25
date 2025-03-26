import java.awt.Frame;
import java.awt.Graphics;

public abstract class SubWindow {
    protected final String CONST_TABLE_MODE_TITLE = "Tablr: Tables Mode";
    protected final String CONST_TABLE_COLUMN_MODE_TITLE = "Tablr: Table Design Mode";
    protected final String CONST_TABLE_ROW_MODE_TITLE = "Tablr: Table Rows Mode";
    protected TableManager tableManager;

    public SubWindow(TableManager tableManager) {
        this.tableManager = tableManager;
    }
    /**
     * Handles a given mouse event
     */
    public abstract void handleMouseEvent(Frame frame, CanvasWindow window, int x, int y, int clickCount);

    /**
     * Handles a given keyboard event
     */
    public abstract void handleKeyEvent(CanvasWindow window, int keyCode, char keyChar, boolean isControlDown);

    /**
     * Draws the relevant mode
     */
    public abstract void drawMode(Frame frame, Graphics graphics);

    //region Locating
    /**
     * Locate table entry in 1D after clicking
     */
    public int getIdx1D(int height, int hscale, int width, int wscale, int x, int y, int xoffset, int yoffset) {
        int entryHeight = height / hscale;
        int entryWidth = width / wscale;

        int entryCol = (x-xoffset)/ entryWidth;
        int entryRow = (y-yoffset)/ entryHeight;
        int entryIdx = (entryRow*wscale)+entryCol;
        return Math.max(0,entryIdx);
    }

    /**
     * Locate table entry in 2D after clicking
     */
    public int[] getIdx2D(int height, int hscale, int width, int wscale,int x, int y, int xoffset, int yoffset) {
        int[] position = {0,0};
        int entryHeight = height / hscale;
        int entryWidth = width/wscale;
        int entryRow = (y-yoffset)/ entryHeight;
        int entryCol = (x-xoffset)/ entryWidth;
        position[0] = Math.max(0,entryRow);
        position[1] = Math.max(0,entryCol);
        return position;
    }
    //endregion
}
