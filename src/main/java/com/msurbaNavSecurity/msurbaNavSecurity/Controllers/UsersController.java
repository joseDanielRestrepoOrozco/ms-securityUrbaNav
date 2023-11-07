package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Role;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.RoleRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.EncryptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/private/users")
public class UsersController {
    @Autowired
    private UserRepository theUserRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private RoleRepository theRoleRepository;


    /**
     * Listado de usuarios
     * @return listado de objetos de tipo User
     */
    @GetMapping("")
    public List<User> index() {
        return this.theUserRepository.findAll();
    }

    public boolean validarCorreo(User newUser){
        List<User> theUsers = this.theUserRepository.findAll();
        for (User user : theUsers) {
            if(user.getEmail().equals(newUser.getEmail()) || user.getPhone().equals(newUser.getPhone())){
                return true;
            }
        }
        return false;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser) {
        newUser.setPassword(encryptionService.convertirSHA256(newUser.getPassword()));
        if(!(this.validarCorreo(newUser))){
            return this.theUserRepository.save(newUser);
        }
        return null;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("list")
    public List<User> storeList(@RequestBody List<User> ListUser) {
        List<User> savedUsers = new ArrayList<>();
        for (User user : ListUser) {
            user.setPassword(encryptionService.convertirSHA256(user.getPassword()));
            User savedUser = this.theUserRepository.save(user);
            savedUsers.add(savedUser);
        }
        return savedUsers;
    }

    /**
     * Mostrar un solo usuario
     * @param id identificador del usuario
     * @return un objeto de tipo User
     */
    @GetMapping("{id}")
    public User show(@PathVariable String id) {
        User theUser = this.theUserRepository
                .findById(id)
                .orElse(null);
        return theUser;
    }

    /**
     * Actualizar un usuario
     * @param id identificador de un usuario
     * @param theNewUser el objeto actualizado
     * @return null || el usuario
     */
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

    /**
     * Eliminar un usuario
     * @param id identificador del usuario
     */
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


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("all")
    public void destroyAll() {
        List<User> theUsers = this.index();
        for (User user : theUsers) {
            this.theUserRepository.delete(user);
        }
    }


    @PutMapping("{user_id}/role/{role_id}")
    public User matchUserRole(@PathVariable String user_id,
                              @PathVariable String role_id) {
        User theActualUser = this.theUserRepository.findById(user_id)
                .orElse(null);
        Role theActualRole = this.theRoleRepository.findById(role_id)
                .orElse(null);
        if (theActualUser != null && theActualRole != null) {
            theActualUser.setRole(theActualRole);
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }


    @PutMapping("{user_id}/role")
    public User unMatchUserRole(@PathVariable String user_id) {
        User theActualUser = this.theUserRepository.findById(user_id)
                .orElse(null);
        if (theActualUser != null) {
            theActualUser.setRole(null);
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }

}
