import java.util.ArrayList;

public class ErrorChecker {

    public static boolean validTableName(Table table, ArrayList<Table> tables){
        if(table == null){
            return true;
        }
        if(table.getName().equals("")){
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

    private static boolean validEmail(String str){
        int count = 0;
        for(char chr: str.toCharArray()){
            if(chr=='@'){
                count++;
            }
        }
        return count==1;
    }

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

    private static boolean validColDefaultValue(Column col){
        if(col==null || col.getType().equals("Boolean")){
            return true;
        }
        String dValue = col.getDefaultValue();
        return dValue.isEmpty() && col.allowsBlanks_()
                || col.getType().equals("String") && !dValue.isEmpty()
                || col.getType().equals("Integer") && validInt(dValue)
                || col.getType().equals("Email") && validEmail(dValue);

    }

    private static boolean validColName(Column col,ArrayList<Column> cols){
        if(col == null){
            return true;
        }
        if(col.getName().equals("")){
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


    public static boolean validColumn(Column col,ArrayList<Column> cols){
        return validColName(col,cols)&&validColDefaultValue(col);
    }


}
