package db;

import domain.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryInMemory implements PersonRepository {

    private List<Person> persons = new ArrayList<>();

    @Override
    public void add(Person person) {
        if (persons.contains(person))
            throw new IllegalArgumentException("Person " + person.getName() + " already exists!");
        persons.add(person);
    }

    @Override
    public Person get(String name) {
        return persons.stream().filter(p -> p.getName() == name).findAny().orElse(null);
    }

    @Override
    public List<Person> getAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public void remove(Person person) {
        persons.remove(person);
    }

}
