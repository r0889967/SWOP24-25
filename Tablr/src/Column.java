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

    public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }



    public void switchBooleanDefaultValue(){
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
}
