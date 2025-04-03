package Types;

import java.util.regex.Pattern;

public class IntegerType implements CustomType{
    
    @Override
    public boolean isValid(String value) {
        String regex = "^-?(0|[1-9]\\d*)$";
        return Pattern.matches(regex, value);
    }
}
