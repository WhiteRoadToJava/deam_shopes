package dev.mohammad.dreamshopes.repository;

import dev.mohammad.dreamshopes.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
