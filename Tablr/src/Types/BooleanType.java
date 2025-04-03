package Types;

public class BooleanType implements CustomType {
    
    @Override
    public boolean isValid(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }
}
