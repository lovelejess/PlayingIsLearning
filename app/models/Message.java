package models;

import enums.MessageLevel;

/**
 * User: Charles
 * Date: 4/30/13
 */
public class Message {

    private String message;
    private MessageLevel level;

    public Message(String message, MessageLevel level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public MessageLevel getLevel() {
        return level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLevel(MessageLevel level) {
        this.level = level;
    }
}
