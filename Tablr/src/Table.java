import java.util.ArrayList;

public class Table {

    private String name;
    private ArrayList<Column> cols = new ArrayList<Column>();
    private ArrayList<ArrayList<Cell>> rows = new ArrayList<ArrayList<Cell>>();
    private boolean selected = false;

    Table(String name){
        this.name = name;
    }

    public boolean isSelected(){
        return selected;
    }

    public void select(){
        selected = true;
    }

    public void unselect(){
        selected = false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addRow(ArrayList<Cell> row){
        rows.add(row);
    }

    public ArrayList<ArrayList<Cell>> getRows(){
        return rows;
    }

    public void addCol(Column col){
        cols.add(col);
    }

    public ArrayList<Column> getCols(){
        return cols;
    }

    public void render(){
        for(Column col : cols){
            col.render();
        }
        for(ArrayList<Cell> row : rows){
            for(Cell cell : row){
                cell.render();
            }
        }

    }


}
