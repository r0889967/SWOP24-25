import java.util.ArrayList;
import java.util.regex.Pattern;

public class Table {
    private String name;
    private String oldName;
    private final ArrayList<Column> cols = new ArrayList<>();
    private final ArrayList<Row> rows = new ArrayList<>();
    private Column selectedColumn = null;
    private Row selectedRow = null;
    private int colSequenceNumber = 1;
    private final ArrayList<Observer> observers = new ArrayList<>();

    Table(String name){
        this.name = name;
        this.oldName = name;
    }
    //region Getters and setters
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

    /**
     * retrieves the currently selected column
     */
    public Column getSelectedCol(){
        return selectedColumn;
    }
    //endregion
    //region Column Management
    /**
     * creates and adds a new column with a default generated name
     */
    public void addCol(){
        Column col = new Column(generateColumnName());
        if (selectedColumn == null || validColumn(selectedColumn)) {
            if (selectedColumn == null){
                selectedColumn = col;
            }
            while (!validColName(col)) {
                name = generateColumnName();
                col.setName(name);
            }
            cols.add(col);
            fillCells();
            notifyObservers();
        }
    }

    /**
     * delete the currently selected column if any
     */
    public void deleteCol(){
        if (selectedColumn != null){
            int idx = this.cols.indexOf(selectedColumn);
            cols.remove(selectedColumn);
            selectedColumn = null;
            synchronize(idx);
            notifyObservers();
        }
    }

    /**
     * generate an incremental name
     */
    private String generateColumnName(){
        return "Column"+this.colSequenceNumber++;
    }


    /**
     * edit column attributes
     */

    public void removeLastColNameChar(){
        if (selectedColumn != null) {
            String colName = selectedColumn.getName();
            if (!colName.isEmpty()) {
                colName = colName.substring(0, colName.length() - 1);
                selectedColumn.setName(colName);
            }
            notifyObservers();
        }
    }

    public void appendCharToColName(char keyChar){
        if (selectedColumn != null) {
            String colName = selectedColumn.getName();
            colName = colName + keyChar;
            selectedColumn.setName(colName);
            notifyObservers();
        }
    }

    public void editColType(){
        if (selectedColumn != null){
            selectedColumn.switchType();
            notifyObservers();
        }
    }

    public void editColAllowsBlank(){
        if (selectedColumn != null) {
            selectedColumn.invertAllowBlank();
            notifyObservers();
        }
    }

    /**
     * Select the column at given index
     */
    public void selectCol(int idx){
        if (validColumn(selectedColumn) && idx >= 0 && idx < cols.size()){
            selectedColumn = cols.get(idx);
        }
    }

    /**
     * Unselect the currently selected column if it is in a valid state
     */
    public void unselectCol(){
        if (validColumn(selectedColumn)) {
            selectedColumn = null;
        }
    }
    //endregion
    //region Row Management
    /**
     * creates and adds a new row
     */
    public void addRow(){
        Row row = new Row();
        rows.add(row);
        for (Column c : this.cols) {
            row.addCell(c.getDefaultValue(),c.getType());
        }
        notifyObservers();
    }

    /**
     * deletes the selected row
     */
    public void deleteRow(){
        if (selectedRow != null){
            rows.remove(selectedRow);
            selectedRow = null;
            notifyObservers();
        }
    }

    /**
     * retrieve the selected row
     */
    public Row getSelectedRow(){
        return selectedRow;
    }

    /**
     * edit the selected cell's value
     */
    public void editCellValue(char keyChar) {
        Cell cell = getSelectedCell();
        if (cell != null){
            int idx = getSelectedRow().getCells().indexOf(cell);
            if (idx != -1){
                cell.editValue(keyChar);
                notifyObservers();
            }
        }
    }


    /**
     * select the row at given index
     */
    public void selectRow(int idx) {
        if (idx >= 0 && idx < rows.size()){
            selectedRow = rows.get(idx);
        }
    }

    /**
     * unselect the currently selected row
     */
    public void unSelectRow(){
        selectedRow = null;
    }

    //endregion
    //region Cell Management
    /**
     * retrieves all cells belonging to a certain column
     */
    private ArrayList<Cell> getCellsByCol(Column col){
        int idx = cols.indexOf(col);
        //No column no cells
        if (idx < 0 || idx >= cols.size()){
            return new ArrayList<>();
        }
        ArrayList<Cell> cells = new ArrayList<>();
        for(Row row : rows){
            cells.add(row.getCells().get(idx));
        }
        return cells;
    }

    /**
     * retrieve the currently selected cell
     */
    public Cell getSelectedCell(){
        //No column or row no cell
        if (selectedColumn == null || selectedRow == null){
            return null;
        }
        int colIdx = cols.indexOf(selectedColumn);
        return selectedRow.getCellByIdx(colIdx);
    }

    /**
     * select the cell at a given row and column index
     */
    public void selectCell(int ridx,int cidx) {
        if (ridx >= 0 && ridx < rows.size() && cidx >= 0 && cidx < cols.size()){
            selectedRow = rows.get(ridx);
            selectedColumn = cols.get(cidx);
        }
    }

    /**
     * unselect the currently selected cells
     */
    public void unSelectCell() {
        selectedRow = null;
        selectedColumn = null;
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
        for (Row row : rows){
            row.addCell("","String");
        }
    }
    //endregion
    //region Validation


    /**
     * check if string is valid email
     */
    private boolean validEmail(String str){
        String emailRegex = "[^@]*@[^@]*";
        return Pattern.matches(emailRegex, str);
    }

    /**
     * check if string is valid integer
     */
    private boolean validInt(String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        //Valid if: positive or negative, no leading zero's or exactly 0
        String regex = "-?([0-9]|[1-9][0-9]+)";
        return Pattern.matches(regex, str);
    }

    /**
     * check if col has valid type
     */
    public boolean validColType(Column col){
        if (col == null) {
            return true;
        }
        for (Cell cell : getCellsByCol(col)) {
            String cellValue = cell.getValue();
            if (cellValue.isEmpty()) {
                continue;
            }
            if (!cell.hasValidValue()) {
                return false;
            }
        }
        return true;
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
        if (col == null){
            return true;
        }
        String dValue = col.getDefaultValue();
        //Blank value is valid if blanks are allowed
        if (col.allowsBlanks() && dValue.isEmpty()) {
            return true;
        }
        //Else check case by case
        return switch (col.getType()) {
            case "String" -> !dValue.isEmpty();
            case "Email" -> validEmail(dValue);
            case "Boolean" -> true; //No user input
            case "Integer" -> validInt(dValue);
            default -> false;
        };

    }

    /**
     * check if col has valid name
     */
    public boolean validColName(Column col){
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
    public boolean validColumn(Column col){
        Boolean v1 = validColName(col);
        Boolean v2 = validColType(col);
        Boolean v3 = validColAllowBlanks(col);
        Boolean v4 = validColDefaultValue(col);
        return v1 && v2 && v3 && v4;
    }

    /**
     * check if all cols of table are valid
     */
    public boolean allValidColumns(){
        return cols.stream().allMatch(this::validColumn);
    }
    //endregion
    //region Observers

    /**
     * Adds an observer window for this table
     */
    public void addObserver(SubWindow subWindow) {
        observers.add(subWindow);
    }

    /**
     * Remove observer when window is closed
     */
    public void deleteObserver(SubWindow subWindow){
        observers.remove(subWindow);
    }

    /**
     * Redraw appropriate windows
     * Used when internal structure changes
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    //endregion
}
