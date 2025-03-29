import java.util.regex.Pattern;

public class EmailCell extends Cell {
    public EmailCell(String value) {
        super(value);
    }

    @Override
    public void editValue(char keyChar) {
        if (keyChar == '\b') {
            value = value.substring(0, value.length() - 1);
        } else {
            value += keyChar;
        }

    }

    @Override
    public boolean hasValidValue() {
        String emailRegex = "[^@]*@[^@]*";
        return Pattern.matches(emailRegex, value);
    }
}
