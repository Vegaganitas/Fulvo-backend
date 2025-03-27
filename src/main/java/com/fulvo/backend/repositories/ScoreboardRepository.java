package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreboardRepository extends JpaRepository<Scoreboard, Integer> {
    Optional<Scoreboard> findByTeamIdAndTournamentId(Integer teamId, Integer tournamentId);

    boolean existsByTeamAndTournament(Team team, Tournament tournament);

    List<Scoreboard> findAllByTeamId(Integer id);
}
