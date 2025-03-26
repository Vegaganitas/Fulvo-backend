package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
}
