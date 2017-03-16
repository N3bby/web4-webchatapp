package db;

import domain.model.Topic;

import java.util.List;

public interface TopicRepository {

    void add(Topic topic);

    Topic get(String topicName);

    List<Topic> getAll();

    void remove(String topicName);

}
