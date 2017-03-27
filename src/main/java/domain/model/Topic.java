package domain.model;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private String name;
    private List<String> messageIds;

    public Topic(String name) {
        this.name = name;
        this.messageIds = new ArrayList<String>();
    }

    public Topic(String name, List<String> messageIds) {
        setName(name);
        setMessageIds(messageIds);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMessageIds() {
        return new ArrayList<>(messageIds);
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }

    public void addMessage(String message) {
        messageIds.add(message);
    }

}
