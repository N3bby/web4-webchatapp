package db;

import domain.model.Message;
import domain.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageRepositoryInMemory implements MessageRepository {

    Map<String, Message> messageMap = new HashMap<>();

    @Override
    public String add(Message message) {
        if(message.getId() == null) {
            int length = 16;
            String randomId = getRandomId(length);
            while (messageMap.containsKey(randomId)) randomId = getRandomId(length);
            message.setId(randomId);
        }
        messageMap.put(message.getId(), message);
        return message.getId();
    }

    private String getRandomId(int length) {
        String tokens = "abcdefghijklmnopqrstuvwxyz0123456789";
        String randomId = "";
        for(int i = 0; i < length; i++) {
            randomId += tokens.charAt((int) Math.round(Math.random() * (tokens.length() - 1)));
        }
        return randomId;
    }

    @Override
    public Message get(String messageId) {
        return messageMap.get(messageId);
    }

    @Override
    public List<Message> getAllOfPerson(Person person) {
        return messageMap.values().stream()
                .filter(m -> m.getPerson().equals(person))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAll(List<String> messageIds) {
        return messageIds.stream()
                .map(id -> messageMap.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAll() {
        return new ArrayList<>(messageMap.values());
    }

    @Override
    public void remove(String messageId) {
        messageMap.remove(messageId);
    }
}
