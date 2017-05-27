package com.razacx.domain.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PrivateMessage extends Message {

    @NotNull
    @ManyToOne
    @Expose
    private Person to;
    
    private boolean seen;

    public PrivateMessage() {
        super();
        this.seen = false;
    }

    public PrivateMessage(Person from, String message, Person to) {
        super(from, message);
        this.seen = false;
        setTo(to);
    }

    public Person getTo() {
        return to;
    }

    public void setTo(Person to) {
        this.to = to;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
    
}
