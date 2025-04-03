import java.awt.*;

public abstract class SubWindow {
    protected TableManager tableManager;

    protected int xcor;
    protected int ycor;
    protected int width;
    protected int height;
    protected int borderThickness = 20;
    private int closeButtonSize = borderThickness;


    public SubWindow(TableManager tableManager,int xcor, int ycor, int width, int height) {
        this.tableManager = tableManager;
        this.xcor = xcor;
        this.ycor = ycor;
        this.width = width;
        this.height = height;
    }


    /**
     * Handles a given mouse event
     */
    public abstract void handleMouseEvent(int id,int x, int y, int clickCount);

    /**
     * Handles a given keyboard event
     */
    public abstract void handleKeyEvent(int id,int keyCode, char keyChar, boolean isControlDown);

    /**
     * Draws the relevant mode
     */
    public abstract void drawMode(Graphics g);

    public void drawBorders(Graphics g){
        if(this.equals(SubWindowManager.getWindow())) {
            g.setColor(new Color(0,162,232));
        }else{
            g.setColor(new Color(153, 217, 234));
        }
        g.fillRect(xcor, ycor, borderThickness, height);
        g.fillRect(xcor, ycor, width, borderThickness);
        g.fillRect(xcor+width-borderThickness, ycor, borderThickness, height);
        g.fillRect(xcor, ycor+height-borderThickness, width, borderThickness);
    }


    public void drawCloseButton(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xcor+width-3*closeButtonSize, ycor, 2*closeButtonSize,closeButtonSize);
    }

    private void dragL(){
        this.xcor = xcor-6;
    }

    private void dragR(){
        this.xcor = xcor+6;
    }

    private void dragU(){
        this.ycor = ycor-6;
    }

    private void dragD(){
        this.ycor = ycor+6;
    }

    private void resizeUL(){
    }

    private void resizeUR(){

    }

    private void resizeDL(){

    }

    private void resizeDR(){

    }

    public void dragOrResize(int x, int y){
        if(x>xcor && x<xcor+borderThickness){
            if(y>ycor && y<ycor+borderThickness){
                resizeUL();
            }else if(y>ycor+height-borderThickness && y<ycor+height){
                resizeDL();
            }else{
                dragL();
            }
        }else if(x>xcor+width-borderThickness && x<xcor+width){
            if(y>ycor && y<ycor+borderThickness) {
                resizeUR();
            }else if(y>ycor+height-borderThickness && y<ycor+height){
                resizeDR();
            }else{
                dragR();
            }
        }else if(y>ycor && y<ycor+borderThickness){
            dragU();
        }else if(y>ycor+height-borderThickness && y<ycor+height){
            dragD();
        }
    }

    public boolean cursorInside(int x,int y){
        return x>xcor && x < xcor+width && y>ycor && y < ycor+height;
    }

    public boolean cursorInsideCloseButton(int x,int y){
        return x>xcor+width-3*closeButtonSize && x<xcor+width-closeButtonSize && y>ycor && y<ycor+closeButtonSize;
    }

    public boolean beingDragged(int id){
        return id==506;
    }

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
