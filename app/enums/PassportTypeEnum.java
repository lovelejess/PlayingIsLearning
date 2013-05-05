package enums;

/**
 * User: Charles
 * Date: 5/4/13
 */
public enum PassportTypeEnum {

    INDIVIDUAL ("Individual"),
    GROUP ("Group");

    public String label;

    PassportTypeEnum(String label) {
        this.label = label;
    }

}
