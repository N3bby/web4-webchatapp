package com.razacx.domain.service;

import com.razacx.domain.model.Person;

import java.util.List;

public interface IPersonService {

    void addPerson(Person person);

    Person getPerson(String username);

    List<Person> getAllPersons();
    
    void updatePerson(Person person);

    void removePerson(Person person);

    void close();

}
