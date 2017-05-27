package com.razacx.domain.service;


import com.razacx.domain.model.Message;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.PrivateMessage;

import java.util.List;

public interface IMessageService {

    void addMessage(Message message);

    void updateMessage(Message message);

    List<PrivateMessage> getPrivateMessagesBetween(Person p1, Person p2);

    List<PrivateMessage> getPrivateMessagesFor(Person p);
    
    Message getMessageById(long id);
    
    void removeMessage(Message message);

    void close();

}
