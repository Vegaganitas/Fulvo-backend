package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final TeamService teamService;
    private final MatchRepository matchRepository;

    public GenericResponse generateMatches(List<Team> teams) {
        int n = teams.size();
        if(n%2 == 1)
            teams.add(teamService.getTeam(0));

        for(int i=0; i<n-1; i++){
            for(int j = 0; j < n/2; j++){
                Team home = teams.get(j);
                Team away = teams.get(n-1-j);
                matchRepository.save(Match.builder()
                                .homeTeam(home)
                                .awayTeam(away)
                                .date(i)
                                .build());
            }
            teams.add(1, teams.remove(n-1));
        }

        return GenericResponse.builder()
                .name("Partidos creados exitosamente")
                .message("Cantidad de fechas: " + (n-1) + "\n Partidos por fecha: " + (n/2))
                .build();
    }

    public List<Match> getAllMatchesByTournament(Tournament tournament){
        return matchRepository.findAllMatchesByTournament(tournament);
    }

    public List<Match> getMatchesByDate(List<Match> matches, Integer date){
        return matchRepository.findAllMatchesByDate(matches, date)
                .orElseThrow(() -> new RuntimeException("No hay partidos para esa fecha"));
    }

}
