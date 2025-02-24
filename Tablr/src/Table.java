import java.util.ArrayList;

public class Table {

    private String name;
    private String tmpName;
    private boolean nameBeingEdited = false;
    private ArrayList<Column> cols = new ArrayList<Column>();
    private ArrayList<ArrayList<Cell>> rows = new ArrayList<ArrayList<Cell>>();
    private boolean selected = false;

    Table(String name){
        this.name = name;
        this.tmpName = name;
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

    public String getTmpName(){
        return tmpName;
    }

    public void setTmpName(String tmpName){
        this.tmpName = tmpName;
    }

    public boolean isNameBeingEdited(){
        return nameBeingEdited;
    }

    public void setNameBeingEdited(boolean nameBeingEdited){
        this.nameBeingEdited = nameBeingEdited;
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

    public void deleteCol(Column col){
        cols.remove(col);
    }

    public ArrayList<Column> getCols(){
        return cols;
    }



}
