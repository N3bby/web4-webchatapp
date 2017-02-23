package service;

import db.PersonRepository;
import db.PersonRepositoryInMemory;
import domain.model.Person;

import java.util.List;

public class ChatService {

    private PersonRepository personRepository = new PersonRepositoryInMemory();

    public void addPerson(Person person) {
        personRepository.add(person);
    }

    public Person getPerson(String name) {
        return personRepository.get(name);
    }

    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    public void removePerson(Person person) {
        personRepository.remove(person);
    }

}
