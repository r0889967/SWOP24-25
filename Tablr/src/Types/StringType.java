package Types;

public class StringType implements CustomType {
    
    @Override
    public boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
