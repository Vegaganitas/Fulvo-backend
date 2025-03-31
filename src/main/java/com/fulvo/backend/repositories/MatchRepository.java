package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    Optional<List<Match>> findAllMatchesByTournamentAndDate(Tournament tournament, Integer date);

    Optional<List<Match>> findAllByTournament(Tournament tournament);
}
