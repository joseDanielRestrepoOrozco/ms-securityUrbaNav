package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Pqrs;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.PqrsRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pqrs")
public class PqrsController {
    @Autowired
    private PqrsRepository thePqrsRepository;

    @Autowired
    private UserRepository theUserRepository;

    // GET TODOS
    @GetMapping("")
    public List<Pqrs> index(){
        return this.thePqrsRepository.findAll();
    }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pqrs store(@RequestBody Pqrs newPqrs){
        return this.thePqrsRepository.save(newPqrs);
    }


    // GET UNO
    @GetMapping("{id}")
    public Pqrs show(@PathVariable String id){
        Pqrs thePqrs=this.thePqrsRepository
                .findById(id)
                .orElse(null);
        return thePqrs;
    }

    // PUT
    @PutMapping("{id}")
    public Pqrs update(@PathVariable String id,@RequestBody Pqrs theNewPqrs){
        Pqrs theActualPqrs=this.thePqrsRepository
                .findById(id)
                .orElse(null);
        if (theActualPqrs!=null){
            theActualPqrs.setType(theNewPqrs.getType());
            theActualPqrs.setDescription(theNewPqrs.getDescription());
            theActualPqrs.setDate(theNewPqrs.getDate());
            return this.thePqrsRepository.save(theActualPqrs);
        }else{
            return null;
        }
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        Pqrs thePqrs=this.thePqrsRepository
                .findById(id)
                .orElse(null);
        if (thePqrs!=null){
            this.thePqrsRepository.delete(thePqrs);
        }
    }

    // MATCH
    @PutMapping("{pqrs_id}/user/{user_id}")
    public Pqrs matchPqrsUser(@PathVariable String pqrs_id,
                              @PathVariable String user_id) {
        Pqrs theActualPqrs=this.thePqrsRepository.findById(pqrs_id).orElse(null);
        User theActualUser=this.theUserRepository.findById(user_id).orElse(null);

        if(theActualPqrs != null && theActualUser != null){
            theActualPqrs.setUser(theActualUser);
            return this.thePqrsRepository.save(theActualPqrs);
        } else {
            return null;
        }
    }

    // UNMATCH
    @PutMapping("{pqrs_id}/user")
    public Pqrs unMatchPqrsUser(@PathVariable String pqrs_id) {
        Pqrs theActualPqrs=this.thePqrsRepository.findById(pqrs_id).orElse(null);
        if(theActualPqrs != null){
            theActualPqrs.setUser(null);
            return this.thePqrsRepository.save(theActualPqrs);
        } else {
            return null;
        }
    }
}
