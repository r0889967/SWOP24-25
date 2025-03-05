import java.util.ArrayList;

public class Row {
    private boolean isSelected = false;

    private final ArrayList<Cell> cells = new ArrayList<>();

    public boolean isSelected() {
        return isSelected;
    }

    public void select(){
        isSelected = true;
    }

    public void unselect(){
        isSelected = false;
    }

    public ArrayList<Cell> getCells(){
        return cells;
    }

    public void addCell(String value){
        cells.add(new Cell(value));
    }

    public void deleteCell(int idx){
        cells.remove(idx);
    }

    public Cell getSelectedCell() {
        for (Cell cell : cells) {
            if (cell.isSelected()) {
                return cell;
            }
        }
        return null;
    }
}
