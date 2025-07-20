package dev.mohammad.dreamshopes.repository;


import dev.mohammad.dreamshopes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
}
