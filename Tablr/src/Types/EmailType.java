package Types;

import java.util.regex.Pattern;

public class EmailType implements CustomType{
    
    @Override
    public boolean isValid(String value) {
         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, value);
    }

    @Override
    public CustomType nextType() {
        return new BooleanType();
    }

    @Override
    public String getDefaultValue(){
        return "";
    }
}
