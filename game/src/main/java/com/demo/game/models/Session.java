package com.demo.game.models;

import javax.persistence.*;
import java.util.List;

// many-many: one end needs to be owner or main definition point of the relationship

// "session" -> name of database table
@Entity(name = "session") // jpa entity so that we can talk to the database structures
public class Session { // session class will map to conference sessions
    // one instance or row of that data

    //map the table columns to the Java attributes in the Session class
    @Id // -> primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // -> specifies how the primary field gets populated on a new record insert
    private Long session_id; // in the database: serial data type (see DB); it adds an auto sequence to it
    // this means that postgres will generate a sequenced auto-generated number for your primary key whenever you insert a record
    private String session_name;
    private String session_description;
    private Integer session_length;
    // notice names (the same as in the database) -> so JPA will auto-bind to those columns and don't need to annotate them

    @ManyToMany // setting up many-to-many relationship
    @JoinTable( // you have mapping join table in your database
            name = "session_speakers",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;

    public Session() {
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public Integer getSession_length() {
        return session_length;
    }

    public void setSession_length(Integer session_length) {
        this.session_length = session_length;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
