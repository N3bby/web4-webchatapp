package com.razacx.domain.service;

import com.razacx.domain.model.Person;

public interface IPersonService {

    void addPerson(Person person);

    Person getPerson(String username);

    void updatePerson(Person person);

    void removePerson(Person person);

    void close();

}
