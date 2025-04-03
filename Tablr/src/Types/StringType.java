package Types;

public class StringType implements CustomType {
    
    @Override
    public boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public CustomType nextType() {
        return new EmailType();
    }

    @Override
    public String getDefaultValue(){
        return "";
    }
}
