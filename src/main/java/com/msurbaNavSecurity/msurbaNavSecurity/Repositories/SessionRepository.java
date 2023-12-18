package com.msurbaNavSecurity.msurbaNavSecurity.Repositories;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Session;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {

    // Buscar una sesión activa para un usuario específico
    Optional<Session> findByUserAndActive(User user, boolean active);

    // Nuevo método para buscar todas las sesiones de un usuario específico
    @Query("{ 'user._id': ?0 }")
    List<Session> findByUser_Id(String userId);
}
