package com.backend.model.repository;

import com.backend.model.entity.Viajante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViajanteRepository extends JpaRepository<Viajante, Long> {
    Optional<Viajante> findByEmail(String email);
}
