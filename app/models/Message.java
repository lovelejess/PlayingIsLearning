package models;

import enums.MessageLevelEnum;

/**
 * User: Charles
 * Date: 4/30/13
 */
public class Message {

    private String message;
    private MessageLevelEnum level;

    public Message(String message, MessageLevelEnum level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public MessageLevelEnum getLevel() {
        return level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLevel(MessageLevelEnum level) {
        this.level = level;
    }
}
