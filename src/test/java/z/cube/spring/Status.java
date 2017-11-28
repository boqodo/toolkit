package java.z.cube.spring;

public enum Status {

    ENABLED("1","F"),DISABLED("2","S");
    public static final Long MAX=Long.MAX_VALUE;

    private String code;
    private String name;
    private Status(String code,String name){
        this.code=code;
        this.name=name;
    }
}
