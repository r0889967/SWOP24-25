package Domain.Types;

public interface CustomType {
    boolean isValid(String value);
    CustomType nextType();
    String getDefaultValue();
    String getTypeName();
}
