import java.util.Random;

public class Column {

    private String name;
    private String type = "string";
    private boolean allowsBlanks = true;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean allowsBlanks() {
        return allowsBlanks;
    }

    public void setAllowsBlanks(boolean allowsBlanks) {
        this.allowsBlanks = allowsBlanks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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
