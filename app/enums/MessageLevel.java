package enums;

/**
 * User: Charles
 * Date: 5/1/13
 */

public enum MessageLevel {
    ERROR ("Error"),
    WARNING ("Warning"),
    INFO ("Info"),
    OK ("Ok");

    public String label;

    MessageLevel(String label) {
        this.label = label;
    }
}