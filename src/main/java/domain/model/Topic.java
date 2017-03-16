package domain.model;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private String name;
    private List<Message> messageIds;

    public Topic(String name) {
        this.name = name;
        this.messageIds = new ArrayList<Message>();
    }

    public Topic(String name, List<Message> messageIds) {
        setName(name);
        setMessageIds(messageIds);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessageIds() {
        return new ArrayList<>(messageIds);
    }

    public void setMessageIds(List<Message> messageIds) {
        this.messageIds = messageIds;
    }

    public void addMessage(Message message) {
        messageIds.add(message);
    }

}
