package db;

import domain.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryInMemory implements PersonRepository {

    private List<Person> persons = new ArrayList<>();

    public PersonRepositoryInMemory() {
        persons.add(new Person("leviv", "123"));
        persons.add(new Person("somn", "123"));
        get("leviv").addFriend(get("somn"));
    }

    @Override
    public void add(Person person) {
        if (persons.contains(person))
            throw new IllegalArgumentException("Person " + person.getUsername() + " already exists!");
        persons.add(person);
    }

    @Override
    public Person get(String username) {
        return persons.stream().filter(p -> p.getUsername().equals(username)).findAny().orElse(null);
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
