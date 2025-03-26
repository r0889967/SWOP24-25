import java.util.ArrayList;

public class Row {

    private final ArrayList<Cell> cells = new ArrayList<>();

    public ArrayList<Cell> getCells(){
        return cells;
    }

    /**
     * adds a cell to this row with given value
     */
    public void addCell(String value){
        cells.add(new Cell(value));
    }

    /**
     * deletes the cell at give index
     */
    public void deleteCell(int idx){
        if (idx >= 0 && idx < cells.size()){
            cells.remove(idx);
        }
    }

    public Cell getCellByIdx(int colIdx) {
        return cells.get(colIdx);
    }
}
