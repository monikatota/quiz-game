package com.demo.game.repositories;

import com.demo.game.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> { // we are using Session as our data type,
    // and Long refers to primary key

    // we now have Find, Save, Delete, and a bunch of other operations already set up and usable on our Session JPA class
}
