package com.razacx.domain.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Topic {

    @Id
    private String name;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Message.class)
    private List<Message> messages;

    public Topic() {
    }

    public Topic(String name) {
        setName(name);
    }

    public Topic(String name, List<Message> messages) {
        this(name);
        setMessages(messages);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}
