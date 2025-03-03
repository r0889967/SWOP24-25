import java.util.ArrayList;

public class Table {

    private String name;
    private String oldName;
    private ArrayList<Column> cols = new ArrayList<Column>();
    private ArrayList<Row> rows = new ArrayList<Row>();
    private boolean selected = false;
    private int colSequenceNumber = 0;

    Table(String name){
        this.name = name;
        this.oldName = name;
    }

    public int getColSequenceNumber(){
        return colSequenceNumber;
    }

    public void addColSequenceNumber(){
        this.colSequenceNumber++;
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

    public String getOldName(){
        return oldName;
    }

    public void setName(String name){

        this.name = name;
    }

    public void setOldName(String oldName){

        this.oldName = oldName;
    }

    public void addRow(Row row){

        rows.add(row);
    }

    public ArrayList<Row> getRows(){

        return rows;
    }

    public void addCol(Column col){
        cols.add(col);
    }

    public void deleteCol(Column col){
        cols.remove(col);
    }

    public void deleteRow(Row row){
        rows.remove(row);
    }

    public ArrayList<Column> getCols(){

        return cols;
    }



}
