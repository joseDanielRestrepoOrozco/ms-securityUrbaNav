package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Role;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private/roles")
public class RolesController {
    @Autowired
    private RoleRepository theRoleRepository;

    /**
     * Listado de roles
     * 
     * @return listado de objetos de tipo Role
     */
    @GetMapping("")
    public List<Role> index() {
        return this.theRoleRepository.findAll();
    }

    /**
     * Crear un rol
     * 
     * @param newRole Objeto de Role
     * @return el rol guardado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role store(@RequestBody Role newRole) {
        return this.theRoleRepository.save(newRole);
    }

    /**
     * Mostrar un solo rol
     * 
     * @param id identificador del rol
     * @return un objeto de tipo Role
     */
    @GetMapping("{id}")
    public Role show(@PathVariable String id) {
        Role theRole = this.theRoleRepository
                .findById(id)
                .orElse(null);
        return theRole;
    }

    /**
     * Actualizar un rol
     * 
     * @param id         identificador de un rol
     * @param theNewRole el objeto actualizado
     * @return null || el rol
     */
    @PutMapping("{id}")
    public Role update(@PathVariable String id, @RequestBody Role theNewRole) {
        Role theActualRole = this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theActualRole != null) {
            theActualRole.setName(theNewRole.getName());
            theActualRole.setDescription(theNewRole.getDescription());
            return this.theRoleRepository.save(theActualRole);
        } else {
            return null;
        }
    }

    /**
     * Eliminar un rol
     * 
     * @param id identificador del rol
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        Role theRole = this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theRole != null) {
            this.theRoleRepository.delete(theRole);
        }
    }

}
