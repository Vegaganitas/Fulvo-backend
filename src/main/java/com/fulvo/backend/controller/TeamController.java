package com.fulvo.backend.controller;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.dto.team.TeamResponse;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.models.Team;
import com.fulvo.backend.services.TeamService;
import com.fulvo.backend.services.TeamTournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamTournamentService teamTournamentService;

    @PostMapping(value = "create")
    public ResponseEntity<GenericResponse> createTeam(@RequestBody TeamRequest request){
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @GetMapping(value = "team-profile")
    public ResponseEntity<TeamResponse> teamProfile(@RequestBody TeamRequest request){
        Team team = teamService.getTeam(request.getId());
        return ResponseEntity.ok(teamService.getTeamResponse(team));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<GenericResponse> deleteTeam(@RequestBody TeamRequest request){
        return ResponseEntity.ok(teamService.deleteTeam(request));
    }

    @PostMapping(value = "tournament/join")
    public ResponseEntity<GenericResponse> joinTournament(@RequestBody RegistrationDTO request){
        return ResponseEntity.ok(teamTournamentService.joinTournament(request));
    }

    @DeleteMapping(value = "tournament/leave")
    public ResponseEntity<GenericResponse> leaveTournament(@RequestBody RegistrationDTO request){
        return ResponseEntity.ok(teamTournamentService.leaveTournament(request));
    }

    @GetMapping(value = "my-teams")
    public ResponseEntity<List<TeamResponse>> myTeams(){
        return ResponseEntity.ok(teamService.getMyTeams());
    }

    @PostMapping(value = "select")
    public ResponseEntity<TeamResponse> selectTeam(@RequestBody TeamRequest request){
        return ResponseEntity.ok(teamService.selectTeam(request));
    }

    @PostMapping(value = "tournament/accept")
    public ResponseEntity<GenericResponse> acceptInvitation(@RequestBody RegistrationDTO request){
        return ResponseEntity.ok(teamService.acceptInvitation(request));
    }

    @PostMapping(value = "tournament/decline")
    public ResponseEntity<GenericResponse> declineInvitation(@RequestBody RegistrationDTO request) {
        return ResponseEntity.ok(teamService.declineInvitation(request));
    }

}
