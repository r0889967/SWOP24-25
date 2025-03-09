package old;
import java.util.ArrayList;

public class Table {

    private String name;
    private String oldName;
    private final ArrayList<Column> cols = new ArrayList<Column>();
    private final ArrayList<Row> rows = new ArrayList<Row>();
    private boolean selected = false;
    private int colSequenceNumber = 0;
    private int columnEditMode = 0;

    Table(String name){
        this.name = name;
        this.oldName = name;
    }
    //region Getters and setters
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

    //Carefull!, this edits both names, use restoreOldName() or saveName() instead
    public void setBothNames(String name){
        this.name = name;
        this.oldName = name;
    }

    public void editName(char keyChar){
        if (keyChar == '\b') {
            name = (name.substring(0, name.length() - 1));
        } else {
            name = name + keyChar;
        }
    }

    public void restoreOldName(){
        name = oldName;
    }

    public void saveName(){
        oldName = name;
    }

    public ArrayList<Column> getCols(){

        return cols;
    }

    public ArrayList<Row> getRows(){
        return rows;
    }

    public int getColumnEditMode() {
        return this.columnEditMode;
    }

    public void setColumnEditMode(int mode) {
        if (mode > 3){
            mode = 3;
        }
        this.columnEditMode = mode;
    }
    //endregion
    //region Column Management
    public void addCol(){
        Column col = new Column(generateColumnName());
        Column selCol = getSelectedCol();
        if (selCol == null || ErrorChecker.validColumn(selCol,this.cols)) {
            col.select();
            while (!ErrorChecker.validColName(col, this.cols)) {
                name = generateColumnName();
                col.setName(name);
            }
            cols.add(col);
            fillCells();
        }
    }

    public void deleteCol(){
        Column col = getSelectedCol();
        if (col != null){
            cols.remove(col);
            int idx = this.cols.indexOf(col);
            synchronize(idx);
        }
    }

    private String generateColumnName(){
        return "Column"+this.colSequenceNumber++;
    }

    public Column getSelectedCol(){
        for (Column col : this.cols){
            if (col.isSelected()){
                return col;
            }
        }
        return null;
    }

    public void editColAttributes(char keyChar){
        if(this.columnEditMode==0){
            if(keyChar!='\0') {
                editColName(keyChar);
            }
        }else if(this.columnEditMode==1){
            editColType();
        }else if(this.columnEditMode==2){
            editColAllowsBlank();
        }else if(this.columnEditMode==3){
            editColDefaultValue(keyChar);
        }
    }

    private void editColName(char keyChar){
        Column col = getSelectedCol();
        if (col != null) {
            col.editName(keyChar);
        }
    }

    private void editColType(){
        Column col = getSelectedCol();
        if (col != null){
            col.switchType();
        }
    }

    private void editColAllowsBlank(){
        Column col = getSelectedCol();
        if (col != null) {
            col.invertAllowBlank();
        }
    }

    private void editColDefaultValue(char keyChar){
        Column col = getSelectedCol();
        if (col != null) {
            col.editDefaultValue(keyChar);
        }
    }

    public void selectCol(int idx){
        if (ErrorChecker.validColumn(getSelectedCol(),this.cols)){
            unselectCol();
            this.cols.get(idx).select();
        }
    }

    public void unselectCol(){
        Column col = getSelectedCol();
        if (col != null) {
            col.unselect();
        }
    }
    //endregion
    //region Row Management
    public void addRow(){
        Row row = new Row();
        for (Column c : this.cols) {
            row.addCell(c.getDefaultValue());
        }
        rows.add(row);
    }

    public void deleteRow(){
        Row row = getSelectedRow();
        rows.remove(row);
    }

    public Row getSelectedRow(){
        for (Row row : this.rows){
            if (row.isSelected()){
                return row;
            }
        }
        return null;
    }

    public void editCellValue(char keyChar) {
        Cell cell = getSelectedCell();
        if (cell != null){
            int idx = getSelectedRow().getCells().indexOf(cell);
            if (idx != -1){
                String type = cols.get(idx).getType();
                cell.editValue(keyChar, type);
            }
        }
    }

    public void selectRow(int idx) {
        unSelectRow();
        rows.get(idx).select();
    }

    public void unSelectRow(){
        Row row = getSelectedRow();
        if (row != null) {
            row.unselect();
        }
    }

    //endregion
    //region Cell Management
    public ArrayList<Cell> getCellsByCol(Column col){
        int idx = cols.indexOf(col);
        ArrayList<Cell> cells = new ArrayList<>();
        for(Row row : getRows()){
            cells.add(row.getCells().get(idx));
        }
        return cells;
    }

    public Cell getSelectedCell(){
        return getSelectedRow().getSelectedCell();
    }

    public void selectCell(int ridx,int cidx) {
        Cell cell = getSelectedCell();

        if (cell != null) {
            cell.unSelect();
        }
        if (ridx < rows.size()) {
            rows.get(ridx).getCells().get(cidx).select();
        }
    }

    public void unSelectCell() {getSelectedCell().unSelect();
    }

    //delete corresponding cells of rows when a col is deleted
    private void synchronize(int idx){
        for(Row row : rows){
            row.deleteCell(idx);
            if(row.getCells().isEmpty()){
                this.deleteRow();
            }
        }
    }

    //adds cells when column is added and rows exist
    private void fillCells(){
        Column col = getSelectedCol();
        for (Row row : rows){
            row.addCell(col.getDefaultValue());
        }
    }
    //endregion
}
