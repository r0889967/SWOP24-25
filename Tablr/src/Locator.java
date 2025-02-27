

public class Locator {

    public static int getIdx(int height, int hscale, int width, int wscale,int x, int y) {
        int entryHeight = height / hscale;
        int entryWidth = width/wscale;
        int entryCol = x/ entryWidth;
        int entryRow = y/ entryHeight;
        int entryIdx = (entryRow*wscale)+entryCol;
        return entryIdx;
    }
}
