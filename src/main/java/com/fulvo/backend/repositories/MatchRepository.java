package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    public List<Match> findAllByTournamentId(int id);
}
