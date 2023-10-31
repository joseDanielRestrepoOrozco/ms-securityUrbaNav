package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.EncryptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private/users")
public class UsersController {
    @Autowired
    private UserRepository theUserRepository;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("")
    public List<User> index() {
        return this.theUserRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser) {
        newUser.setPassword(encryptionService.convertirSHA256(newUser.getPassword()));
        return this.theUserRepository.save(newUser);
    }

    @GetMapping("{id}")
    public User show(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        return theUser;
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User theNewUser) {
        User theActualUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theActualUser != null) {
            theActualUser.setName(theNewUser.getName());
            theActualUser.setSurname(theNewUser.getSurname());
            theActualUser.setPhone(theNewUser.getPhone());
            theActualUser.setEmail(theNewUser.getEmail());
            theActualUser.setBirthdate(theNewUser.getBirthdate());
            theActualUser.setPassword(theNewUser.getPassword());
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }

    // @PutMapping("encrypting")
    // public void encrypting() {
    // List<User> usuarios = theUserRepository.findAll();

    // for (User usuario : usuarios) {
    // System.out.println("ID: " + usuario.get_id());
    // System.out.println("Nombre de usuario: " + usuario.getName());
    // System.out.println("Contraseña: " + usuario.getPassword());
    // usuario.setPassword(encryptionService.convertirSHA256(usuario.getPassword()));
    // System.out.println("Nueva Contraseña Encriptada: " + usuario.getPassword());
    // this.theUserRepository.save(usuario);
    // }
    // }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theUser != null) {
            this.theUserRepository.delete(theUser);
        }
    }

}
