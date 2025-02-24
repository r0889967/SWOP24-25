import java.awt.*;

public class Locator {

    public static int getIdx(Frame frame, int hscale, int wscale,int x, int y) {
        int entryHeight = frame.getHeight() / hscale;
        int entryWidth = frame.getWidth()/wscale;
        int entryCol = x/ entryWidth;
        int entryRow = y/ entryHeight;
        int entryIdx = (entryRow*wscale)+entryCol;
        return entryIdx;
    }
}
