package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final UserService userService;
    private final TournamentRepository tournamentRepository;

    public GenericResponse createTournament(TournamentRequest request) {
        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .admin(userService.getUser())
                .build();

        tournamentRepository.save(tournament);
        return GenericResponse.builder()
                .name(tournament.getName())
                .build();
    }
}
