public class Column {

    private String name;
    private String type = "String";
    private Boolean allowsBlanks = true;
    private String defaultValue = "";
    
    Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * directly sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * appends given character to name, deletes last character if the given character is backspace.
     */
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

    /**
     * cycles through available types
     */
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

    /**
     * inverts the boolean value of allow blank
     */
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

    /**
     * Edits the default value:
     * If type is string/email: append character or remove last character if given character is backspace.
     * If type is boolean: cycles through allowed values
     * If type is integer: same behaviour as string/email but only accepts numerical characters or backspace
     */
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
}
