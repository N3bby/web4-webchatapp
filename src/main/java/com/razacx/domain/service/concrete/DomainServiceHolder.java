package com.razacx.domain.service.concrete;

import com.razacx.domain.service.IMessageService;
import com.razacx.domain.service.IPersonService;
import com.razacx.domain.service.IServiceHolder;
import com.razacx.domain.service.ITopicService;

import java.util.Properties;

public class DomainServiceHolder implements IServiceHolder {

    private static final DomainServiceHolder instance = new DomainServiceHolder(DomainServiceHolder.getDefaultProperties());

    public static DomainServiceHolder getInstance() {
        return instance;
    }

    public static Properties getDefaultProperties() {
        Properties properties = new Properties();
        properties.setProperty("dbType", "test");
        return properties;
    }

    private IMessageService messageService;
    private IPersonService personService;
    private ITopicService topicService;

    private DomainServiceHolder(Properties properties) {
        messageService = new MessageDomainService(properties);
        personService = new PersonDomainService(properties);
        topicService = new TopicDomainService(properties);
    }

    @Override
    public IMessageService getMessageService() {
        return messageService;
    }

    @Override
    public IPersonService getPersonService() {
        return personService;
    }

    @Override
    public ITopicService getTopicService() {
        return topicService;
    }

}
