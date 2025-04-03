import java.util.ArrayList;
import java.util.List;


public class SubWindowManager {
    private static SubWindow curSubWindow;

    private static List<SubWindow> subWindows = new ArrayList<>();

    public static SubWindow getWindow() {
        return curSubWindow;
    }

    public static List<SubWindow> getSubWindows() {
        return subWindows;
    }


    public static void removeSubWindow(SubWindow subWindow) {
        subWindows.remove(subWindow);
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
            TablesWindow startWindow = new TablesWindow(new TableManager(),0,0,600,600);
            subWindows.add(startWindow);
            curSubWindow = startWindow;
        }
    }



    /**
     * Switches window for table designing
     */
    public static void toTableDesignWindow(TableManager tableManager) {
        subWindows.add(new TableDesignWindow(tableManager,300,300,600,600));
    }

    /**
     * Switches window for table editing
     */
    public static void toTableRowsWindow(TableManager tableManager) {
        subWindows.add(new TableRowsWindow(tableManager,300,300,600,600));
    }


}