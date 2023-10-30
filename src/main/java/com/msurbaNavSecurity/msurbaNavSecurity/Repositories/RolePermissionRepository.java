package com.msurbaNavSecurity.msurbaNavSecurity.Repositories;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.RolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RolePermissionRepository extends MongoRepository<RolePermission, String> {

    // método para encontrar RolePermission con rol y permiso (consultando a la BD)
    @Query("{'role.$id': ObjectId(?0),'permission.$id': ObjectId(?1)}")
    RolePermission getRolePermission(String roleId, String permissionId);
}
