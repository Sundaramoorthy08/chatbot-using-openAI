package com;

public class ChatsModal {
    private String message;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sender;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatsModal(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }
}
