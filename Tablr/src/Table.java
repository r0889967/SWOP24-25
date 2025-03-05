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
        if (ErrorChecker.validColumn(getSelectedCol(),this.cols)){
            while(!ErrorChecker.validColumn(col,this.cols)){
                name = generateColumnName();
                col.setName(name);
            }
        }
        cols.add(col);
    }

    public void deleteCol(){
        Column col = getSelectedCol();
        if (col != null){
            cols.remove(col);
            int idx = this.cols.indexOf(col);
            RowColMediator.synchronize(this,idx);
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
        unselectCol();
        if(idx<this.cols.size()) {
            this.cols.get(idx).select();
        }
    }

    public void unselectCol(){
        Column col = getSelectedCol();
        if(ErrorChecker.validColumn(col, this.cols)) {
            if (col != null) {
                col.unselect();
            }
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

    public void deleteRow(Row row){
        rows.remove(row);
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
    //endregion
}
