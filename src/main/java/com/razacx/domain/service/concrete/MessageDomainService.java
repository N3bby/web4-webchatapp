package com.razacx.domain.service.concrete;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.exception.DBException;
import com.razacx.domain.db.factory.GenericRepositoryFactory;
import com.razacx.domain.db.specification.IJPACriteriaQuerySpecification;
import com.razacx.domain.db.specification.IJPAFunctionSpecification;
import com.razacx.domain.model.Message;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.IMessageService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

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

    //TODO Write tests!!!!
    @Override
    public List<PrivateMessage> getPrivateMessagesBetween(Person p1, Person p2) {

        List<Message> messages = messageRepository.queryList((IJPACriteriaQuerySpecification) cb -> {

            CriteriaQuery<PrivateMessage> query = cb.createQuery(PrivateMessage.class);
            Root<PrivateMessage> root = query.from(PrivateMessage.class);

            Predicate where = cb.or(
                    cb.and(
                            cb.equal(root.get("from"), p1),
                            cb.equal(root.get("to"), p2)
                    ),
                    cb.and(
                            cb.equal(root.get("from"), p2),
                            cb.equal(root.get("to"), p1)
                    )
            );

            query.where(where);
            query.orderBy(cb.desc(root.get("date")));

            return query;

        });

        //Need to cast manually since java is a b****
        List<PrivateMessage> result = new ArrayList<>(messages.size());
        for (Message m : messages) {
            try {
                result.add((PrivateMessage) m);
            } catch (Exception e) {
                throw new DBException("Unable to cast Message to PrivateMessage");
            }
        }
        return result;

    }

    @Override
    public List<PrivateMessage> getPrivateMessagesBetween(Person p1, Person p2, int from, int limit) {

        List<Message> messages = messageRepository.queryList((IJPAFunctionSpecification) () -> (EntityManager em) -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<PrivateMessage> query = cb.createQuery(PrivateMessage.class);
            Root<PrivateMessage> root = query.from(PrivateMessage.class);

            Predicate where = cb.or(
                    cb.and(
                            cb.equal(root.get("from"), p1),
                            cb.equal(root.get("to"), p2)
                    ),
                    cb.and(
                            cb.equal(root.get("from"), p2),
                            cb.equal(root.get("to"), p1)
                    )
            );

            query.where(where);
            query.orderBy(cb.desc(root.get("date")));

            return em.createQuery(query)
                     .setFirstResult(from)
                     .setMaxResults(limit)
                     .getResultList();

        });

        //Need to cast manually since java is a b****
        List<PrivateMessage> result = new ArrayList<>(messages.size());
        for (Message m : messages) {
            try {
                result.add((PrivateMessage) m);
            } catch (Exception e) {
                throw new DBException("Unable to cast Message to PrivateMessage");
            }
        }
        return result;

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
