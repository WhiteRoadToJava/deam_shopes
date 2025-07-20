package dev.mohammad.dreamshopes.repository;

import dev.mohammad.dreamshopes.model.Order;
import dev.mohammad.dreamshopes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> user(User user);

    List<Order> findByUserId(Long userId);
}
