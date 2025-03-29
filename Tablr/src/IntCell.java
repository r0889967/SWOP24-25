import java.util.regex.Pattern;

public class IntCell extends Cell {
    public IntCell(String value) {
        super(value);
    }

    @Override
    public void editValue(char keyChar) {
        if (keyChar == '\b') {
            value = value.substring(0, value.length() - 1);
        } else {
            if(keyChar == '-' || Character.isDigit(keyChar)) {
                value += keyChar;
            }
        }

    }

    @Override
    public boolean hasValidValue() {
        String regex = "-?([0-9]|[1-9][0-9]+)";
        return Pattern.matches(regex, value);
    }
}
