import java.util.Objects;

public class Column {

    private String name;
    private String type = "String";
    private Boolean allowsBlanks = true;
    private String defaultValue = "";

    private boolean isSelected = false;

    Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void editName(char keyChar) {
        if(keyChar=='\b'){
            this.name = this.name.substring(0, this.name.length()-1);
        }else{
            this.name = this.name + keyChar;
        }
    }

    public String getType() {
        return type;
    }

    public void switchType() {
        switch (type) {
            case "String" -> type = ("Email");
            case "Email" -> {
                type = "Boolean";
                defaultValue = "True";
            }
            case "Boolean" -> {
                type = "Integer";
                defaultValue = "0";
            }
            default -> {
                type = "String";
                if (defaultValue.equals("0")){
                    defaultValue = "";
                }
            }
        }
    }

    public boolean allowsBlanks() {
        return allowsBlanks;
    }

    public void invertAllowBlank() {
        allowsBlanks = !allowsBlanks;
        //Correct default value if possible
        if (!allowsBlanks && (defaultValue.isEmpty() || defaultValue.equals("0"))) {
            switch (type){
                case "Integer" -> defaultValue = "0";
                case "Boolean" -> defaultValue = "True";
                default -> defaultValue = "";
            }
        }
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void editDefaultValue(char keyChar) {
        // True -> False -> Blank; skips blank if not allowed
        if (type.equals("Boolean")){
            switch (defaultValue) {
                case "True":
                    defaultValue = "False";
                    break;
                case "False":
                    defaultValue = (allowsBlanks ? "" : "True");
                    break;
                default:
                    defaultValue = ("True");
            }
        }
        else{
            if(keyChar!='\0'){
                if (type.equals("Integer")) {
                    //Default Integer value must consist of numbers
                    if (Character.isDigit(keyChar) || keyChar == '\b') {
                        if (keyChar == '\b') {
                            defaultValue = (defaultValue.substring(0, defaultValue.length() - 1));
                        } else {
                            defaultValue += keyChar;
                        }
                    }
                }
                // Case string or email, no special actions
                else{
                    if (keyChar == '\b') {
                        defaultValue = (defaultValue.substring(0, defaultValue.length() - 1));
                    } else {
                        defaultValue += keyChar;
                    }
                }
            }
        }
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void select(){
        isSelected = true;
    }

    public void unselect(){
        isSelected = false;
    }
}
