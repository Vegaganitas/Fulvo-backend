package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    Optional<Tournament> findByNameAndAdmin(String name, User admin) throws RuntimeException;

    boolean existsByNameAndAdmin(String name, User admin);
}
