package com.razacx.domain.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @NotNull
    @Expose
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Expose
    private String status;

    @ElementCollection(targetClass = Person.class, fetch = FetchType.EAGER)
    private List<Person> friends = new ArrayList<>();

    public Person(String username, String password) {
        setUsername(username);
        setPassword(password);
        status = "Offline";
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        if(username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordMatches(String password) {
        return this.password.equals(password);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addFriend(Person person) {
        if(person == null) throw new NullPointerException("Person cannot be empty");
        if(person.equals(this)) throw new IllegalArgumentException("Cannot add yourself as a friend");
        if(friends.contains(person)) throw new IllegalArgumentException(person.getUsername() + " is already a friend");
        friends.add(person);
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    public List<Person> getFriends() {
        return new ArrayList<>(friends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return username.equals(person.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
