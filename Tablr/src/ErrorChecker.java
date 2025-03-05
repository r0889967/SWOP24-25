import java.util.ArrayList;

public class ErrorChecker {

    //check if table has valid name
    public static boolean validTableName(Table table, ArrayList<Table> tables){
        if(table == null){
            return true;
        }
        if(table.getName().isEmpty()){
            return false;
        }
        for(Table t:tables){
            if(t==table){
                continue;
            }
            if(t.getName().equals(table.getName())){
                return false;
            }
        }
        return true;
    }

    //check if cell has valid value
    private static boolean validCellValue(Cell cell,Column col){
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

    //check if string is valid email
    private static boolean validEmail(String str){
        int count = 0;
        for(char chr: str.toCharArray()){
            if(chr=='@'){
                count++;
            }
        }
        return count==1;
    }

    //check if string is valid integer
    private static boolean validInt(String str){
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

    //check if col has valid type
    public static boolean validColType(Column col){
        Table table = TableManager.getSelectedTable();
        if(col == null || table == null){
            return true;
        }
        String dValue = col.getDefaultValue();
        ArrayList<Cell> cells = table.getCellsByCol(col);
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

    //check if col has valid allowblanks
    public static boolean validColAllowBlanks(Column col){
        Table table = TableManager.getSelectedTable();
        if(col == null||col.allowsBlanks()||table == null){
            return true;
        }
        ArrayList<Cell> cells = table.getCellsByCol(col);
        return cells.stream().noneMatch(cell -> cell.getValue().isEmpty());
    }

    //check if col has valid default value
    public static boolean validColDefaultValue(Column col){
        if(col==null || col.getType().equals("Boolean")){
            return true;
        }
        String dValue = col.getDefaultValue();
        return dValue.isEmpty() && col.allowsBlanks()
                || col.getType().equals("String") && !dValue.isEmpty()
                || col.getType().equals("Integer") && validInt(dValue)
                || col.getType().equals("Email") && validEmail(dValue);

    }

    //check if col has valid name
    public static boolean validColName(Column col,ArrayList<Column> cols){
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

    //check if a given col is valid
    public static boolean validColumn(Column col,ArrayList<Column> cols){
        Boolean v1 = validColName(col,cols);
        Boolean v2 = validColType(col);
        Boolean v3 = validColAllowBlanks(col);
        Boolean v4 = validColDefaultValue(col);
        return v1 && v2 && v3 && v4;
    }

    //check if all cols of table are valid
    public static boolean allValidColumns(ArrayList<Column> cols){
        return cols.stream().allMatch(col -> validColumn(col,cols));
    }


}
