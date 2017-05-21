package com.razacx.domain.service;

import com.razacx.domain.model.Topic;

import java.util.List;

public interface ITopicService {

    void addTopic(Topic topic);

    void updateTopic(Topic topic);
    
    Topic getTopicByName(String name);
    List<Topic> getAllTopics();

    void removeTopic(Topic topic);

    void close();

}
