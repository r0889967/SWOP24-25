import java.util.ArrayList;

public class Row {


    private ArrayList<Cell> cells = new ArrayList<>();

    

    public ArrayList<Cell> getCells(){
        return cells;
    }

    /**
     * adds a cell to this row with given value
     */
    public void addCell(String value,String type){
        if(type.equals("String")) {
            cells.add(new StringCell(value));
        }else if(type.equals("Integer")) {
            cells.add(new IntCell(value));
        }else if (type.equals("Email")) {
            cells.add(new EmailCell(value));
        }else if (type.equals("Boolean")) {
            cells.add(new BoolCell(value));
        }
    }

    public void addCellatIdx(String value,String type,int idx){
        if(type.equals("String")) {
            cells.add(idx,new StringCell(value));
        }else if(type.equals("Integer")) {
            cells.add(idx,new IntCell(value));
        }else if (type.equals("Email")) {
            cells.add(idx,new EmailCell(value));
        }else if (type.equals("Boolean")) {
            cells.add(idx,new BoolCell(value));
        }
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
