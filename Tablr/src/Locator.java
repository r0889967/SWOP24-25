

public class Locator {

    public static int getIdx(int height, int hscale, int width, int wscale,int x, int y, int xoffset, int yoffset) {
        int entryHeight = height / hscale;
        int entryWidth = width/wscale;
        int entryCol = (x-xoffset)/ entryWidth;
        int entryRow = (y-yoffset)/ entryHeight;
        int entryIdx = (entryRow*wscale)+entryCol;
        return Math.max(0,entryIdx);
    }

    public static int[] getIdx2D(int height, int hscale, int width, int wscale,int x, int y, int xoffset, int yoffset) {
        int[] position = {0,0};
        int entryHeight = height / hscale;
        int entryWidth = width/wscale;
        int entryCol = (x-xoffset)/ entryWidth;
        int entryRow = (y-yoffset)/ entryHeight;
        position[0] = Math.max(0,entryCol);
        position[1] = Math.max(0,entryRow);
        return position;
    }
}
