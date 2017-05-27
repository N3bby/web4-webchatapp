package com.razacx.domain.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Person {

    public enum Gender {
        MALE,
        FEMALE
    }
    
    @Id
    @NotNull
    @Expose
    private String username;

    @NotNull
    @Expose
    private String email;
        
    @Expose
    private Gender gender;
    
    @NotNull
    private String password;

    @NotNull
    @Expose
    private String status;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Person.class)
    private List<Person> friends = new ArrayList<>();

    public Person() {
        this.status = "Offline";
    }

    public Person(String username, String password) {
        this();
        setUsername(username);
        setPassword(password);
    }

    public Person(String username, String email, Gender gender, String password) {
        this(username, password);
        setEmail(email);
        setGender(gender);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
