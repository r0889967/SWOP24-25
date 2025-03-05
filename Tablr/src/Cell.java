public class Cell {
    private String value;
    private boolean isSelected;


    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void select() {
        isSelected = true;
    }

    public void unSelect() {
        isSelected = false;
    }

    public void editValue(char keyChar, String type) {
        //Filter non-numbers for Integer fields
        if (type.equals("Integer")) {
            if (Character.isDigit(keyChar) || keyChar == '\b') {
                if (keyChar == '\b') {
                    value = value.substring(0, value.length() - 1);
                } else {
                    value += keyChar;
                }
            }
        }
        else {
            if (keyChar == '\b') {
                value = value.substring(0, value.length() - 1);
            } else {
                value += keyChar;
            }
        }
    }
}
