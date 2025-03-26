import java.util.ArrayList;

public class Table {

    private String name;
    private String oldName;
    private final ArrayList<Column> cols = new ArrayList<>();
    private final ArrayList<Row> rows = new ArrayList<>();
    private boolean selected = false;
    private int colSequenceNumber = 1;
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

    /**
     * sets a new name without regard for saving the old name
     */
    //Warning!, this method edits both names, use restoreOldName() or saveName() instead
    public void setBothNames(String name){
        this.name = name;
        this.oldName = name;
    }

    /**
     * edits the table name
     */
    public void editName(char keyChar){
        if (keyChar == '\b') {
            name = (name.substring(0, name.length() - 1));
        } else {
            name = name + keyChar;
        }
    }

    /**
     * restores the table name to its value before edits
     */
    public void restoreOldName(){
        name = oldName;
    }

    /**
     * saves the table name and wipes the stored old name
     */
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

    /**
     * sets the selected field. Any value outside [0,3] range is set to the closest boundary
     */
    public void setColumnEditMode(int mode) {
        if (mode > 3){
            mode = 3;
        }
        else if (mode < 0){
            mode = 0;
        }
        this.columnEditMode = mode;
    }
    //endregion
    //region Column Management
    /**
     * creates and adds a new column with a default generated name
     */
    public void addCol(){
        Column col = new Column(generateColumnName());
        Column selCol = getSelectedCol();
        if (selCol == null || validColumn(selCol,this.cols)) {
            if (selCol == null){
                col.select();
            }
            while (!validColName(col, this.cols)) {
                name = generateColumnName();
                col.setName(name);
            }
            cols.add(col);
            fillCells();
        }
    }

    /**
     * delete the currently selected column if any
     */
    public void deleteCol(){
        Column col = getSelectedCol();
        if (col != null){
            int idx = this.cols.indexOf(col);
            cols.remove(col);
            synchronize(idx);
        }
    }

    /**
     * generate an incremental name
     */
    private String generateColumnName(){
        return "Column"+this.colSequenceNumber++;
    }

    /**
     * retrieves the currently selected column
     */
    public Column getSelectedCol(){
        for (Column col : this.cols){
            if (col.isSelected()){
                return col;
            }
        }
        return null;
    }

    /**
     * Edits the value of the selected field with the given character
     * If selected field is:
     * Name: appends character or delete last character if backspace
     * Type: cycles through allowed values regardless of input
     * Allow blanks: Inverts the allowed value regardless of input
     * Default value:
     * If the type is name/field: appends character or deletes last character if backspace was pressed
     * If the type is integer: same behaviour but only accepts numerical characters and backspace
     * If the type is boolean: cycles through allowed values
     */
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

    /**
     * Select the column at given index
     */
    public void selectCol(int idx){
        if (validColumn(getSelectedCol(),cols)){
            unselectColNoCheck();
            cols.get(idx).select();
        }
    }

    /**
     * Unselect the currently selected column if it is in a valid state
     */
    public void unselectCol(){
        Column col = getSelectedCol();
        if (col != null){
            if (validColumn(col,cols)) {
                col.unselect();
            }
        }
    }

    /**
     * Warning: Does not check for valid column
     */
    private void unselectColNoCheck(){
        Column col = getSelectedCol();
        if (col != null) {
            col.unselect();
        }
    }
    //endregion
    //region Row Management
    /**
     * creates and adds a new row
     */
    public void addRow(){
        Row row = new Row();
        for (Column c : this.cols) {
            row.addCell(c.getDefaultValue());
        }
        rows.add(row);
    }

    /**
     * deletes the selected row
     */
    public void deleteRow(){
        Row row = getSelectedRow();
        rows.remove(row);
    }

    /**
     * retrieve the selected row
     */
    public Row getSelectedRow(){
        for (Row row : this.rows){
            if (row.isSelected()){
                return row;
            }
        }
        return null;
    }

    /**
     * edit the selected cell's value
     */
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

    /**
     * select the row at given index
     */
    public void selectRow(int idx) {
        rows.get(idx).select();
    }

    /**
     * unselect the currently selected row
     */
    public void unSelectRow(){
        Row row = getSelectedRow();
        if (row != null) {
            row.unselect();
        }
    }

    //endregion
    //region Cell Management
    /**
     * retrieves all cells belonging to a certain column
     */
    private ArrayList<Cell> getCellsByCol(Column col){
        int idx = cols.indexOf(col);
        ArrayList<Cell> cells = new ArrayList<>();
        for(Row row : getRows()){
            cells.add(row.getCells().get(idx));
        }
        return cells;
    }

    /**
     * retrieve the currently selected cell
     */
    public Cell getSelectedCell(){
        if (getSelectedRow() == null){
            return null;
        }
        return getSelectedRow().getSelectedCell();
    }

    /**
     * select the cell at a given row and column index
     */
    public void selectCell(int ridx,int cidx) {
        if (ridx < rows.size()) {
            rows.get(ridx).getCells().get(cidx).select();
        }
    }

    /**
     * unselect the currently selected cells
     */
    public void unSelectCell() {
        Cell cell = getSelectedCell();
        if (cell != null) {
            cell.unSelect();
        }
    }

    /**
     * when column is deleted: remove the associated cells in rows
     */
    private void synchronize(int idx){
        for(Row row : rows){
            row.deleteCell(idx);
            if(row.getCells().isEmpty()){
                this.deleteRow();
            }
        }
    }

    /**
     * when column is added: append new cells to all rows
     */
    private void fillCells(){
        Column col = getSelectedCol();
        for (Row row : rows){
            row.addCell(col.getDefaultValue());
        }
    }
    //endregion
    //region Validation
    /**
     * check if cell has valid value
     */
    private boolean validCellValue(Cell cell,Column col){
        if(col == null){
            return true;
        }
        String value = cell.getValue();
        return value.isEmpty() && col.allowsBlanks()
        || col.getType().equals("String") && !value.isEmpty()
        || col.getType().equals("Boolean") && (value.equals("true") || value.equals("false")
                || value.equals("True") || value.equals("False"))
        || col.getType().equals("Integer") && validInt(value)
        || col.getType().equals("Email") && validEmail(value);
    }

    /**
     * check if string is valid email
     */
    private boolean validEmail(String str){
        int count = 0;
        for(char chr: str.toCharArray()){
            if(chr=='@'){
                count++;
            }
        }
        return count==1;
    }

    /**
     * check if string is valid integer
     */
    private boolean validInt(String str){
        if(str.isEmpty()){
            return false;
        }
        for(char chr: str.toCharArray()){
            if(!Character.isDigit(chr)){
                return false;
            }
        }
        return String.valueOf(Integer.parseInt(str)).equals(str);
    }

    /**
     * check if col has valid type
     */
    public boolean validColType(Column col){
        if(col == null){
            return true;
        }
        String dValue = col.getDefaultValue();
        ArrayList<Cell> cells = getCellsByCol(col);
        if(dValue.isEmpty() && cells.isEmpty()){
            return true;
        }

        for(Cell cell: cells){
            if(cell.getValue().isEmpty()){
                continue;
            }
            if(!validCellValue(cell,col)){
                return false;
            }

        }
        return validColDefaultValue(col);
    }

    /**
     * check if col has valid allow blanks
     */
    public boolean validColAllowBlanks(Column col){
        if(col == null||col.allowsBlanks()){
            return true;
        }
        ArrayList<Cell> cells = getCellsByCol(col);
        return cells.stream().noneMatch(cell -> cell.getValue().isEmpty());
    }

    /**
     * check if col has valid default value
     */
    public boolean validColDefaultValue(Column col){
        if(col==null || col.getType().equals("Boolean")){
            return true;
        }
        String dValue = col.getDefaultValue();
        return dValue.isEmpty() && col.allowsBlanks()
                || col.getType().equals("String") && !dValue.isEmpty()
                || col.getType().equals("Integer") && validInt(dValue)
                || col.getType().equals("Email") && validEmail(dValue);

    }

    /**
     * check if col has valid name
     */
    public boolean validColName(Column col,ArrayList<Column> cols){
        if(col == null){
            return true;
        }
        if(col.getName().isEmpty()){
            return false;
        }
        for(Column c:cols){
            if(c==col){
                continue;
            }
            if(c.getName().equals(col.getName())){
                return false;
            }
        }
        return true;
    }

    /**
     * check if a given col is valid
     */
    public boolean validColumn(Column col,ArrayList<Column> cols){
        Boolean v1 = validColName(col,cols);
        Boolean v2 = validColType(col);
        Boolean v3 = validColAllowBlanks(col);
        Boolean v4 = validColDefaultValue(col);
        return v1 && v2 && v3 && v4;
    }

    /**
     * check if all cols of table are valid
     */
    public boolean allValidColumns(){
        return cols.stream().allMatch(col -> validColumn(col,cols));
    }
    //endregion
}
