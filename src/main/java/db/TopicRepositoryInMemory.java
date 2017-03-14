package db;

import domain.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicRepositoryInMemory implements TopicRepository {

    Map<String, Topic> topicMap = new HashMap<>();

    @Override
    public void add(Topic topic) {
        topicMap.put(topic.getName(), topic);
    }

    @Override
    public Topic get(String topicName) {
        return topicMap.get(topicName);
    }

    @Override
    public List<Topic> getAll() {
        return new ArrayList<>(topicMap.values());
    }

    @Override
    public void remove(String topicName) {
        topicMap.remove(topicName);
    }
}
