package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.PaymentMethod;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.Pqrs;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.PqrsRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

///REVISAR NO FUNCIONA MATCH
@CrossOrigin
@RestController
@RequestMapping("/private/pqrs")
public class PqrsController {
    @Autowired
    private PqrsRepository thePqrsRepository;

    @Autowired
    private UserRepository theUserRepository;

    // GET TODOS
    @GetMapping("")
    public List<Pqrs> index() {
        return this.thePqrsRepository.findAll();
    }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pqrs store(@RequestBody Pqrs newPqrs) {
        return this.thePqrsRepository.save(newPqrs);
    }

    // GET UNO
    @GetMapping("{id}")
    public Pqrs show(@PathVariable String id) {
        Pqrs thePqrs = this.thePqrsRepository
                .findById(id)
                .orElse(null);
        return thePqrs;
    }

    @GetMapping("user/{id}")
    public List<Pqrs> showPqrsUser(@PathVariable String id) {
        List<Pqrs> listPqrs = this.thePqrsRepository.findAll();
        List<Pqrs> listPqrsxUse = new ArrayList<>();
        for (Pqrs pqrs : listPqrs) {
            System.out.println(pqrs.getUser());
            if (pqrs.getUser() != null) {
                if (pqrs.getUser().get_id().equals(id)) {
                    listPqrsxUse.add(pqrs);
                }
            }
        }
        return listPqrsxUse;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("list")
    public List<Pqrs> storeList(@RequestBody List<Pqrs> ListPqrs) {
        List<Pqrs> savedPqrs = new ArrayList<>();
        for (Pqrs pqrs : ListPqrs) {
            Pqrs savedPqr = this.thePqrsRepository.save(pqrs);
            savedPqrs.add(savedPqr);
        }
        ;
        return savedPqrs;
    }

    // PUT
    @PutMapping("{id}")
    public Pqrs update(@PathVariable String id, @RequestBody Pqrs theNewPqrs) {
        Pqrs theActualPqrs = this.thePqrsRepository
                .findById(id)
                .orElse(null);
        if (theActualPqrs != null) {
            theActualPqrs.setType(theNewPqrs.getType());
            theActualPqrs.setDescription(theNewPqrs.getDescription());
            theActualPqrs.setDate(theNewPqrs.getDate());
            return this.thePqrsRepository.save(theActualPqrs);
        } else {
            return null;
        }
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        Pqrs thePqrs = this.thePqrsRepository
                .findById(id)
                .orElse(null);
        if (thePqrs != null) {
            this.thePqrsRepository.delete(thePqrs);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("all")
    public void destroyAll() {
        List<Pqrs> thePqrs = this.index();
        for (Pqrs pqr : thePqrs) {
            this.thePqrsRepository.delete(pqr);
        }
    }

    // MATCH
    @PutMapping("{pqrs_id}/user/{user_id}")
    public Pqrs matchPqrsUser(@PathVariable String pqrs_id,
            @PathVariable String user_id) {
        Pqrs theActualPqrs = this.thePqrsRepository.findById(pqrs_id).orElse(null);
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);

        if (theActualPqrs != null && theActualUser != null) {
            theActualPqrs.setUser(theActualUser);
            return this.thePqrsRepository.save(theActualPqrs);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{user_id}/pqrs")
    public List<Pqrs> matchList(@RequestBody List<Pqrs> ListPqrs, @PathVariable String user_id) {
        List<Pqrs> savedpqrs = new ArrayList<>();
        for (Pqrs pqrs : ListPqrs) {
            System.out.println(pqrs.get_id());
            savedpqrs.add(this.matchPqrsUser(pqrs.get_id(), user_id));
        }
        return savedpqrs;
    }

    // UNMATCH
    @PutMapping("{pqrs_id}/user")
    public Pqrs unMatchPqrsUser(@PathVariable String pqrs_id) {
        Pqrs theActualPqrs = this.thePqrsRepository.findById(pqrs_id).orElse(null);
        if (theActualPqrs != null) {
            theActualPqrs.setUser(null);
            return this.thePqrsRepository.save(theActualPqrs);
        } else {
            return null;
        }
    }
}
