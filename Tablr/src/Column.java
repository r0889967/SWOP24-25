public class Column {

    private String name;
    private String type;
    private boolean allowsBlanks;
    private String defaultValue;

    Column(String name, String type, boolean allowsBlanks, String defaultValue) {
        this.name = name;
        this.type = type;
        this.allowsBlanks = allowsBlanks;
        this.defaultValue = defaultValue;
    }

    public void render(){

    }

}
