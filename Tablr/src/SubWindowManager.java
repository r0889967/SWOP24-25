import java.util.ArrayList;
import java.util.List;


public class SubWindowManager {
    private static SubWindow curSubWindow;

    private static List<SubWindow> subWindows = new ArrayList<>();

    private static int defaultWidth = 900;
    private static int defaultHeight = 600;

    public static SubWindow getWindow() {
        return curSubWindow;
    }

    public static List<SubWindow> getSubWindows() {
        return subWindows;
    }


    public static void removeSubWindow(SubWindow subWindow) {
        int idx = subWindows.indexOf(subWindow);
        subWindows.remove(subWindow);
        curSubWindow = subWindows.get(idx-1);
    }

    public static void switchSubWindow(int x,int y,int id) {
        if(!subWindows.contains(curSubWindow)||!curSubWindow.beingDragged(id) && !curSubWindow.cursorInside(x,y)) {
            for (SubWindow subWindow : subWindows) {
                if (subWindow.cursorInside(x, y)) {
                    curSubWindow = subWindow;
                    break;
                }
            }
        }
    }


    public static void initialize(){
        if(subWindows.isEmpty()){
            TablesWindow startWindow = new TablesWindow(new TableManager(),0,0,900,600);
            subWindows.add(startWindow);
            curSubWindow = startWindow;
        }
    }



    /**
     * Switches window for table designing
     */
    public static void openTableDesignWindow(TableManager tableManager) {
        subWindows.add(new TableDesignWindow(tableManager,300,300,defaultWidth,defaultHeight));
    }

    /**
     * Switches window for table editing
     */
    public static void openTableRowsWindow(TableManager tableManager) {
        subWindows.add(new TableRowsWindow(tableManager,300,300,defaultWidth,defaultHeight));
    }


}