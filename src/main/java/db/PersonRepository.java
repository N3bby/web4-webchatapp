package db;

import domain.model.Person;

import java.util.List;

public interface PersonRepository {

    void add(Person person);

    Person get(String name);

    List<Person> getAll();

    void remove(Person person);

}
