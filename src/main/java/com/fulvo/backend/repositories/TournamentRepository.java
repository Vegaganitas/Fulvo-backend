package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    List<Tournament> findAllByAdmin(User admin);

    boolean existsByNameAndAdmin(String name, User admin);
}
