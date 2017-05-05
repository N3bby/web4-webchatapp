package com.razacx.domain.service.concrete;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.factory.GenericRepositoryFactory;
import com.razacx.domain.model.Message;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.IMessageService;

import java.util.List;
import java.util.Properties;

public class MessageDomainService implements IMessageService {

    private IGenericRepository<Message> messageRepository;

    public MessageDomainService(Properties properties) {
        messageRepository = new GenericRepositoryFactory<Message>()
                .getRepository((String) properties.get("dbType"), Message.class);
    }

    @Override
    public void addMessage(Message message) {
        messageRepository.add(message);
    }

    @Override
    public void updateMessage(Message message) {
        messageRepository.update(message);
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesBetween(Person p1, Person p2) {
        return null;
        //Todo
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesBetween(Person p1, Person p2, int from, int limit) {
        return null;
        //Todo
    }

    @Override
    public void removeMessage(Message message) {
        messageRepository.remove(message);
    }

    @Override
    public void close() {
        messageRepository.close();
    }

}
