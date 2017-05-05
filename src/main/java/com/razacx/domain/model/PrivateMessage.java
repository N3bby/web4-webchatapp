package com.razacx.domain.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class PrivateMessage extends Message {

    @NotNull
    private Person to;

    public PrivateMessage(Person from, String message, Person to) {
        super(from, message);
        setTo(to);
    }

    public Person getTo() {
        return to;
    }

    public void setTo(Person to) {
        this.to = to;
    }

}
