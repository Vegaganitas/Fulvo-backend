package com.fulvo.backend.repositories;
import com.fulvo.backend.models.Scoreboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScoreboardRepository extends JpaRepository<Scoreboard, Integer> {
    List<Scoreboard> findAllByTournamentId(int id);
}
