package db;

import domain.model.Message;
import domain.model.Person;

import java.util.List;

public interface MessageRepository {

    //Returns new id if applicable
    String add(Message message);

    Message get(String messageId);

    List<Message> getAllOfPerson(Person person);

    List<Message> getAll(List<String> messageIds);

    List<Message> getAll();

    void remove(String messageId);

}
