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

    public void unselect() {
        isSelected = false;
    }


}
