import java.util.ArrayList;

public class ColumnManager {

    private static int editMode = 0;

    private static String generateName(){
        Table table = TableManager.getSelectedTable();
        table.addColSequenceNumber();
        return "Column"+table.getColSequenceNumber();
    }

    public static int getEditMode(){
        return editMode;
    }

    public static void setEditMode(int mode){
        if(mode>3){
            mode = 3;
        }
        editMode = mode;
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

    private static boolean hasValidDefaultValue(Column col){
        if(col==null || col.getType().equals("Boolean")){
            return true;
        }
        String dValue = col.getDefaultValue();
        return dValue.isEmpty() && col.allowsBlanks_()
                || col.getType().equals("String") && !dValue.isEmpty()
                || col.getType().equals("Integer") && validInt(dValue)
                || col.getType().equals("Email") && validEmail(dValue);

    }

    private static boolean hasValidName(Column col){
        Table table = TableManager.getSelectedTable();

        if(col == null){
            return true;
        }
        if(col.getName().equals("")){
            return false;
        }
        for(Column c:getCols(table)){
            if(c==col){
                continue;
            }
            if(c.getName().equals(col.getName())){
                return false;
            }
        }
        return true;
    }

    private static boolean hasValidRowValues(Column col){
        return true;
    }

    public static boolean isColValid(Column col){
        return hasValidName(col)&&hasValidDefaultValue(col)&&hasValidRowValues(col);
    }


    public static void selectCol(Table table,int idx){
        ArrayList<Column> cols = table.getCols();
        Column col = getSelectedCol();
        if(isColValid(col)) {
            if (col != null) {
                col.unselect();
            }
            cols.get(idx).select();
        }
    }

    public static void unselectCol(){
        Column col = getSelectedCol();
        if(hasValidName(col)) {
            if (col != null) {
                col.unselect();
            }
        }
    }

    public static Column getSelectedCol(){
        Table table = TableManager.getSelectedTable();
        if(table!=null) {
            ArrayList<Column> cols = table.getCols();
            for (int i = 0; i < cols.size(); i++) {
                if (cols.get(i).isSelected()) {
                    return cols.get(i);
                }
            }
        }
        return null;
    }

    private static void editColName(char keyChar){
        Column col = getSelectedCol();
        String name = col.getName();
        if(keyChar=='\b'){
            col.setName(name.substring(0, name.length()-1));
        }else{
            col.setName(name+keyChar);
        }
    }

    private static void editColType(){
        Column col = getSelectedCol();
        if(col.getType().equals("String")){
            col.setType("Email");
        }else if(col.getType().equals("Email")){
            col.setType("Boolean");
            col.setDefaultValue("True");
        }else if(col.getType().equals("Boolean")){
            col.setType("Integer");
        }else{
            col.setType("String");
        }

    }

    private static void editColallowsBlank(){
        Column col = getSelectedCol();
        if(col.allowsBlanks().equals("☑")){
            col.setAllowsBlanks("☐");
        }else{
            col.setAllowsBlanks("☑");
        }

    }

    private static void editColDefaultValue(char keyChar){
        Column col = getSelectedCol();
        String defaultValue = col.getDefaultValue();

        if(col.getType().equals("Boolean")) {
            if(col.allowsBlanks().equals("☑")){
                if(defaultValue.equals("True")){
                    col.setDefaultValue("False");
                }else if(defaultValue.equals("False")){
                    col.setDefaultValue("");
                }else{
                    col.setDefaultValue("True");
                }

            }else{
                if(defaultValue.equals("True")){
                    col.setDefaultValue("False");
                }else{
                    col.setDefaultValue("True");
                }

            }

        }else {

            if (keyChar == '\b') {
                col.setDefaultValue(defaultValue.substring(0, defaultValue.length() - 1));
            } else {
                col.setDefaultValue(defaultValue + keyChar);
            }
        }

    }

    public static void editColAttributes(char keyChar){
        if(editMode==0){
            if(keyChar!='\0') {
                editColName(keyChar);
            }
        }else if(editMode==1){
            editColType();
        }else if(editMode==2){
            editColallowsBlank();
        }else if(editMode==3){
            if(keyChar!='\0') {
                editColDefaultValue(keyChar);
            }
        }
    }

    public static ArrayList<Column> getCols(Table table){
        return table.getCols();
    }

    private static int getColIdx(Table table, Column col){
        for(int i=0;i<table.getCols().size();i++){
            if(col==table.getCols().get(i)){
                return i;
            }
        }
        return 0;
    }

    //create and add a new col
    public static void createAndAddCol(){
        if(hasValidName(getSelectedCol())) {
            Table table = TableManager.getSelectedTable();
            String name = generateName();
            Column col = new Column(name);
            while(!hasValidName(col)) {
                name = generateName();
                col.setName(name);
            }
            table.addCol(col);
            if(!getCols(table).isEmpty()) {
                RowColMediator.synchronize2(table);
            }
        }
    }

    //delete selected col
    public static void deleteCol(){
        Table table = TableManager.getSelectedTable();
        Column col = getSelectedCol();
        int idx = getColIdx(table,col);
        if(col!=null) {
            table.deleteCol(col);
        }
        RowColMediator.synchronize(table,idx);
    }
}
