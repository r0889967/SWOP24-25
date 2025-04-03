public abstract class Cell {
    protected String value;
    protected boolean allowsBlank = true;

    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setAllowsBlank(boolean allowsBlank) {
        this.allowsBlank = allowsBlank;
    }

    /**
     * Edits the cell's value:
     * If given type is string/email/boolean: append character or remove last character if given character is backspace.
     * If given type is integer: same behaviour as string/email but only accepts numerical characters or backspace
     */
    public abstract void editValue(char keyChar);

    public abstract boolean hasValidValue();
}
