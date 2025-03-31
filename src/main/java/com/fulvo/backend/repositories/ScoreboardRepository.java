package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreboardRepository extends JpaRepository<Scoreboard, Integer> {
    boolean existsByTeamAndTournament(Team team, Tournament tournament);

    Optional<List<Scoreboard>> findAllByTeamId(Integer id);

    Optional<List<Scoreboard>> findAllByTournamentId(Integer id);

    void deleteByTeamAndTournament(Team team, Tournament tournament);

}
