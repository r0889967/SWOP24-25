import java.util.ArrayList;

public class Row {
    private boolean isSelected = false;

    private final ArrayList<Cell> cells = new ArrayList<>();

    public boolean isSelected() {
        return isSelected;
    }

    /**
     * selects this row
     */
    public void select(){
        isSelected = true;
    }

    /**
     * unselects this row
     */
    public void unselect(){
        isSelected = false;
    }

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
        cells.remove(idx);
    }

    /**
     * retrieves the selected cell
     */
    public Cell getSelectedCell() {
        for (Cell cell : cells) {
            if (cell.isSelected()) {
                return cell;
            }
        }
        return null;
    }
}
