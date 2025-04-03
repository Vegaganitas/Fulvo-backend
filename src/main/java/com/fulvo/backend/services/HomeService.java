package com.fulvo.backend.services;

import com.fulvo.backend.dto.match.DateResponse;
import com.fulvo.backend.dto.tournament.TournamentRequest;
import com.fulvo.backend.dto.tournament.TournamentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final MatchService matchService;

}
