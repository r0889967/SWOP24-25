import java.util.ArrayList;

public class Table {

    private String name;
    ArrayList<Column> cols = new ArrayList<Column>();


    ArrayList<ArrayList<Cell>> rows = new ArrayList<ArrayList<Cell>>();

    Table(String name){
        this.name = name;
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

    public void addCol(Column col){
        cols.add(col);
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
