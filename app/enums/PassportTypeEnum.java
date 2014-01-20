package enums;

public enum PassportTypeEnum {

    INDIVIDUAL ("Individual"),
    GROUP ("Group");

    public String label;

    PassportTypeEnum(String label) {
        this.label = label;
    }

}
