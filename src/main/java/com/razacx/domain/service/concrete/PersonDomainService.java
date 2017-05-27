package com.razacx.domain.service.concrete;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.factory.GenericRepositoryFactory;
import com.razacx.domain.db.specification.IJPACriteriaQuerySpecification;
import com.razacx.domain.model.Person;
import com.razacx.domain.service.IPersonService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Properties;

public class PersonDomainService implements IPersonService {

    private IGenericRepository<Person> personRepository;

    public PersonDomainService(Properties properties) {
        personRepository = new GenericRepositoryFactory<Person>()
                .getRepository((String) properties.get("dbType"), Person.class);
        
        //Add default people
        addPerson(new Person("abc", "123"));
        addPerson(new Person("def", "123"));
        addPerson(new Person("ghi", "123"));
        
    }

    @Override
    public void addPerson(Person person) {
        personRepository.add(person);
    }

    @Override
    public Person getPerson(String username) {
        return personRepository.query((IJPACriteriaQuerySpecification) cb -> {
            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> root = query.from(Person.class);
            query.where(cb.equal(root.get("username"), username));
            return query;
        });
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.update(person);
    }

    @Override
    public void removePerson(Person person) {
        personRepository.remove(person);
    }

    @Override
    public void close() {
        personRepository.close();
    }

}
