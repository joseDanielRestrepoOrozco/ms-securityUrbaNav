package com.msurbaNavSecurity.msurbaNavSecurity.Repositories;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Pqrs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PqrsRepository extends MongoRepository<Pqrs, String> {

}
