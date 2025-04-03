package Types;

public class BooleanType implements CustomType {
    
    @Override
    public boolean isValid(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    @Override
    public CustomType nextType() {
        return new IntegerType();
    }

    @Override
    public String getDefaultValue(){
        return "True";
    }
}
