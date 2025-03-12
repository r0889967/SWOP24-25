public class Cell {
    private String value;
    private boolean isSelected;


    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    /**
     * selects this cell
     */
    public void select() {
        isSelected = true;
    }

    /**
     * unselects this cell
     */
    public void unSelect() {
        isSelected = false;
    }

    /**
     * Edits the cell's value:
     * If given type is string/email/boolean: append character or remove last character if given character is backspace.
     * If given type is integer: same behaviour as string/email but only accepts numerical characters or backspace
     */
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
