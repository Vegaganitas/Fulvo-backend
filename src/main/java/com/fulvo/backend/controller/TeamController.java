package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.team.JoinTournamentRequest;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping(value = "create")
    public ResponseEntity<GenericResponse> createTeam(@RequestBody TeamRequest request){
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<GenericResponse> deleteTeam(@RequestBody TeamRequest request){
        return ResponseEntity.ok(teamService.deleteTeam(request));
    }

    @PostMapping(value = "tournament/join")
    public ResponseEntity<GenericResponse> joinTournament(@RequestBody JoinTournamentRequest request){
        return ResponseEntity.ok(teamService.joinTournament(request));
    }

}
