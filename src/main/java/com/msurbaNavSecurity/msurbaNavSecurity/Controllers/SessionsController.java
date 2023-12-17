package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Session;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.SessionRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository theSessionRepository;

    @Autowired
    private UserRepository theUserRepository;

    /**
     * Listado de sesiones
     *
     * @return listado de objetos de tipo Session
     */
    @GetMapping("")
    public List<Session> index() {
        return this.theSessionRepository.findAll();
    }

    /**
     * Crear un session
     *
     * @param newSession Objeto de Session
     * @return el session guardado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Session store(@RequestBody Session newSession) {
        return this.theSessionRepository.save(newSession);
    }

    /**
     * Mostrar un solo session
     *
     * @param id identificador del session
     * @return un objeto de tipo Session
     */
    @GetMapping("{id}")
    public Session show(@PathVariable String id) {
        Session theSession = this.theSessionRepository
                .findById(id)
                .orElse(null);
        return theSession;
    }

    @PutMapping("{id}")
    public Session update(@PathVariable String id,@RequestBody Session theNewUser){
        Session theActualSession=this.theSessionRepository
                .findById(id)
                .orElse(null);
        if (theActualSession!=null){
            theActualSession.setActive(theNewUser.getActive());
            theActualSession.setCode(theNewUser.getCode());
            return this.theSessionRepository.save(theActualSession);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        Session theSession=this.theSessionRepository
                .findById(id)
                .orElse(null);
        if (theSession!=null){
            this.theSessionRepository.delete(theSession);
        }
    }

    @PutMapping("{session_id}/user/{user_id}")
    public Session matchSessionUser(@PathVariable String session_id, @PathVariable String user_id) {
        Session theActualSession = this.theSessionRepository.findById(session_id)
                .orElse(null);
        User theActualUser = this.theUserRepository.findById(user_id)
                .orElse(null);
        if (theActualSession != null && theActualUser != null) {
            theActualSession.setUser(theActualUser);
            return this.theSessionRepository.save(theActualSession);
        } else {
            return null;
        }
    }

    @PutMapping("{session_id}/user")
    public Session unMatchSessionUser(@PathVariable String session_id) {
        Session theActualSession = this.theSessionRepository.findById(session_id)
                .orElse(null);
        if (theActualSession != null) {
            theActualSession.setUser(null);
            return this.theSessionRepository.save(theActualSession);
        } else {
            return null;
        }
    }
}
