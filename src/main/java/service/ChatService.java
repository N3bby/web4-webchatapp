package service;

import db.*;
import domain.model.Message;
import domain.model.Person;
import domain.model.Topic;

import java.util.List;

public class ChatService {

    private PersonRepository personRepository = new PersonRepositoryInMemory();
    private TopicRepository topicRepository = new TopicRepositoryInMemory();
    private MessageRepository messageRepository = new MessageRepositoryInMemory();

    public ChatService() {
        Topic topic = new Topic("Topic 1");
        topicRepository.add(topic);
        topicRepository.add(new Topic("Topic 2"));
        topicRepository.add(new Topic("Topic 3"));
        addMessage(topic, new Message(personRepository.get("leviv"), "test1"));
        addMessage(topic, new Message(personRepository.get("leviv"), "test2"));
        addMessage(topic, new Message(personRepository.get("leviv"), "test3"));
    }

    public Person getPerson(String name) {
        return personRepository.get(name);
    }

    public Topic getTopic(String topic) {
        return topicRepository.get(topic);
    }

    public List<Topic> getTopics() {
        return topicRepository.getAll();
    }

    public List<Message> getMessagesById(List<String> messageIds) {
        return messageRepository.getAll(messageIds);
    }

    public void addMessage(Topic topic, Message message) {
        String newId = messageRepository.add(message);
        topic.addMessage(newId);
    }

}
