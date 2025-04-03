package Domain;
import Domain.Types.*;

public class Column {

    private String name;
    private CustomType type = new StringType();
    private Boolean allowsBlanks = true;
    private String defaultValue = "";
    
    Column(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {return name;}
    public String getType() {return type.getTypeName();}
    public Boolean getAllowsBlanks() {return allowsBlanks;}
    public String getDefaultValue() {return defaultValue;}

    // Setters
    public void setName(String name) {this.name = name;}
    public void setType(CustomType type) {this.type = type;}
    public void setAllowsBlanks(Boolean allowsBlanks) {this.allowsBlanks = allowsBlanks;}
    public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}

    /**
     * cycles through available types
     */
    public void switchType() {
        type = type.nextType();
    }

    /**
     * inverts the boolean value of allow blank
     */
    public void invertAllowBlank() {
        allowsBlanks = !allowsBlanks;
        //Correct default value if possible
        if (!allowsBlanks && (defaultValue.isEmpty() || defaultValue.equals("0"))) {
            defaultValue = type.getDefaultValue();
        }
    }

    /**
     * switches the default value of a boolean type column between "True" and "False"
     * and sets it to "" if the column allows blanks
     */
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
