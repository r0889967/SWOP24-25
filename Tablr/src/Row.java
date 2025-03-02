import java.util.ArrayList;

public class Row {
    private boolean isSelected = false;


    private ArrayList<Cell> cells = new ArrayList<>();

    public ArrayList<Cell> getCells(){
        return cells;
    }

    public void addCell(Cell cell){
        cells.add(cell);
    }

    public void deleteCell(int idx){
        cells.remove(idx);
    }


}
