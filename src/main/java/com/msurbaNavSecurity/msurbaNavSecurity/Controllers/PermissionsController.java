package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Permission;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private/permissions")
public class PermissionsController {

    @Autowired
    private PermissionRepository thePermissionRepository;

    /**
     * Lista los permisos
     * @return listado de objetos de tipo Permission
     */
    @GetMapping("")
    public List<Permission> index() {
        return this.thePermissionRepository.findAll();
    }


    /**
     * Guarda un permiso
     * @param newPermission
     * @return el permiso guardado
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permission store(@RequestBody Permission newPermission) {
        return this.thePermissionRepository.save(newPermission);
    }


    /**
     * Muestra un solo permiso
     * @param id identificador del permiso
     * @return un objeto Permission
     */
    @GetMapping("{id}")
    public Permission show(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        return thePermission;
    }

    /**
     * @param id
     * @param theNewPermission
     * @return
     */
    @PutMapping("{id}")
    public Permission update(@PathVariable String id, @RequestBody Permission theNewPermission) {
        Permission theActualPermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (theActualPermission != null) {
            theActualPermission.setUrl(theNewPermission.getUrl());
            theActualPermission.setMethod(theNewPermission.getMethod());
            theActualPermission.setDescription(theNewPermission.getDescription());
            return this.thePermissionRepository.save(theActualPermission);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (thePermission != null) {
            this.thePermissionRepository.delete(thePermission);
        }
    }
}
