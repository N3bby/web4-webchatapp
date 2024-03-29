package com.razacx.domain.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue
    @Expose
    private long id;

    @NotNull    
    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date date;
    
    @NotNull
    @Expose
    @ManyToOne
    private Person from;
    
    @NotNull
    @Size(min = 3, max = 255)
    @Expose
    private String message;

    public Message() {
        setDate(Calendar.getInstance().getTime());
    }

    public Message(Person from, String message) {
        this();
        setFrom(from);
        setMessage(message);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getFrom() {
        return from;
    }

    public void setFrom(Person from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
    
}
