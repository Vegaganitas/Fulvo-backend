package com.fulvo.backend.services;

import com.fulvo.backend.dto.match.MatchDTO;
import com.fulvo.backend.models.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchTournamentService {

    private final MatchService matchService;
    private final TournamentService tournamentService;
    private final ScoreboardService scoreboardService;

    public MatchDTO matchEnded (MatchDTO request){
        Match match = matchService.getMatch(request.getId());

        tournamentService.authTournament(match.getTournament().getId());
        match.setHomeGoals(request.getHomeGoals());
        match.setAwayGoals(request.getAwayGoals());
        scoreboardService.updateByMatch(match);
        matchService.save(match);
        return matchService.getMatchResponse(match);
    }

}
