package com.msurbaNavSecurity.msurbaNavSecurity.Repositories;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
