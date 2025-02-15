package com.example.chatuyg;

public class MessageModel {
    private String name, description, uid;

    public MessageModel() {
    }

    public MessageModel(String name, String description, String uid) {
        this.name = name;
        this.description = description;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
