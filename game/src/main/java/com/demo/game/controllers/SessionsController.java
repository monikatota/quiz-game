package com.demo.game.controllers;

import com.demo.game.models.Session;
import com.demo.game.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// list, get, create, update, delete - this fills out all of our CRUD operations for a proper REST resource

@RestController // this will respond to payloads incoming and outgoing as JSON REST endpoints
@RequestMapping("api/v1/sessions") // this annotiation tells the router to what the mapping URL will look like
// all request to that URL will be sent to this controller
public class SessionsController {

    // use spring to inject or autowire the sessions repository
    // so spring will auto wire this when our sessionscontrollers is build
    // it will create an instance of the session repository and put it onto our class
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
        // findAll - this builds this method for us and it is going to query all of the sessions in the database
        // and return them as a list of Session objects

        // our data type is returning a list of sessions, and spring mvc will then pass that over to Jackson,
        // which is serialization library, which will turn those sessions into JSON and return them back to the caller
    }

    @GetMapping // GET is pulling that ff the URL and injecting it into out method automatically
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
        // in the controller we are auto-mashaling the session,
        // which will return the specific session back to the caller in JSON payload
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // specify the exact response that you want to occur, 201 in HTTP world
    public Session create(@RequestBody final Session session){
        // Spring MVC takes all the attributes in a JSON payload and automatically mashalling them into a session object
        return sessionRepository.saveAndFlush(session);
        // save and flush: when you are using JPA and entities, you can save objects as you are working with it,
        // but it actually doesnot get commited to the database until you flush it
        // so the SessionRepository and the JPA repositories provide the method that will do thi at once
        // as you pass in JPA entities to it
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delte(@PathVariable Long id){
        // Also need to check for children records before deleting
        // if a sessions hs children records, this would not allow to delete them.
        // we'll het a foreign key constraint violation when we issue the DELETE call to the database.
        // extra homework: would be to put in logic which allows the deleting of the children records for sessions
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    // for update there is PUT and PATCH. the PUT will replace all of the attributes on the record that you are updating
    // the PATCH will allow just a prtion of the aatributes to be updated
    public Session update (@PathVariable Long id, @RequestBody Session session){
        // before updating a record, we fist need to take existing one
        Session existingSession = sessionRepository.getOne(id);
        // takes the existing session and copies the incoming session data onto it
        // + ignore properties on the entites or Java object that we do not want to copy over from one to the another
        // we ignore PK because we do not want to replace it
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);

        // TODO: Add validation that all attributes are passed in, otherwise will be updated with null
        //  otherwise return 400 bad payload
    }
}
