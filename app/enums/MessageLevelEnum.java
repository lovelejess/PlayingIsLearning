package enums;

/**
 * User: Charles
 * Date: 5/1/13
 */

public enum MessageLevelEnum {

    ERROR ("Error"),
    WARNING ("Warning"),
    INFO ("Info"),
    OK ("Ok");

    public String label;

    MessageLevelEnum(String label) {
        this.label = label;
    }

}