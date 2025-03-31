package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.models.Match;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public GenericResponse generateMatches(List<Scoreboard> teams, boolean roundTrip) {
        int n = teams.size();
        for(int i=0; i<n-1; i++){
            for(int j = 0; j < n/2; j++){
                Scoreboard home = roundTrip? teams.get(j) : teams.get(n-1-j);
                Scoreboard away = roundTrip? teams.get(n-1-j) : teams.get(j);
                Match match = Match.builder()
                        .homeTeam(home)
                        .awayTeam(away)
                        .date(roundTrip? n+i: i+1)
                        .tournament(home.getTournament())
                        .build();
                matchRepository.save(match);
            }
            teams.add(1, teams.remove(n-1));
        }

        return GenericResponse.builder()
                .name("Partidos creados exitosamente")
                .message("Cantidad de fechas: " + (n-1) + "\n Partidos por fecha: " + (n/2))
                .build();
    }


    ///////DELETE MATCHES

    public List<Match> getMatchesByDate(Tournament tournament, Integer date){
        return matchRepository.findAllMatchesByTournamentAndDate(tournament, date)
                .orElseThrow(() -> new RuntimeException("No hay partidos para esa fecha"));
    }

    public List<Match> findAllByTournament(Tournament tournament) {
        return matchRepository.findAllByTournament(tournament)
                .orElseThrow(()-> new RuntimeException("No hay partidos para este torneo"));
    }

    public void deleteAll(List<Match> matchList) {
        matchRepository.deleteAll(matchList);
    }
}
