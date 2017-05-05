package com.razacx.domain.service.concrete;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.factory.GenericRepositoryFactory;
import com.razacx.domain.db.specification.IJPACriteriaQuerySpecification;
import com.razacx.domain.model.Topic;
import com.razacx.domain.service.ITopicService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class TopicDomainService implements ITopicService {

    private IGenericRepository<Topic> topicRepository;

    public TopicDomainService(Properties properties) {
        topicRepository = new GenericRepositoryFactory<Topic>()
                .getRepository((String) properties.get("dbType"), Topic.class);
    }

    @Override
    public void addTopic(Topic topic) {
        topicRepository.add(topic);
    }

    @Override
    public Topic getTopicByName(String name) {
        return topicRepository.query((IJPACriteriaQuerySpecification) cb -> {
            CriteriaQuery<Object> query = cb.createQuery();
            Root<Topic> root = query.from(Topic.class);
            query.where(cb.equal(root.get("name"), name));
            return query;
        });
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.queryList((IJPACriteriaQuerySpecification) CriteriaBuilder::createQuery);
    }

    @Override
    public void removeTopic(Topic topic) {
        topicRepository.remove(topic);
    }

    @Override
    public void close() {
        topicRepository.close();
    }

}
