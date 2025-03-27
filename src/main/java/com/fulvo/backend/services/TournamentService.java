package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.models.Scoreboard;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final UserService userService;
    private final TournamentRepository tournamentRepository;
    private final ScoreboardService scoreboardService;

    public GenericResponse createTournament(TournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .admin(admin)
                .build();
        boolean exist = tournamentRepository.existsByNameAndAdmin(request.getName(), admin);
        if(exist)
            throw new RuntimeException("Ya tenes un torneo con ese nombre");

        tournamentRepository.save(tournament);
        return GenericResponse.builder()
                .name(tournament.getName())
                .build();
    }

    public GenericResponse deleteTournament(TournamentRequest request) {
        User admin = userService.getUser();
        Tournament tournament = tournamentRepository.findByNameAndAdmin(request.getName(), admin)
                .orElseThrow(() -> new RuntimeException("No se encontro el torneo"));
        List <Scoreboard> scoreboardList = scoreboardService.findAllByTournament(tournament);
        if (!scoreboardList.isEmpty()){
            scoreboardService.deleteAll(scoreboardList);
        }
        tournamentRepository.deleteById(tournament.getId());
        return GenericResponse.builder()
                .name("Torneo eliminado")
                .message(request.getName())
                .build();
    }
}
