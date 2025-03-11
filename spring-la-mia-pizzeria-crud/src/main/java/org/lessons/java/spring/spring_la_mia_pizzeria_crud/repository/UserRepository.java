package org.lessons.java.spring.spring_la_mia_pizzeria_crud.repository;

import java.util.Optional;

import org.lessons.java.spring.spring_la_mia_pizzeria_crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String username);

}
