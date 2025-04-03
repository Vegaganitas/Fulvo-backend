package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.dto.team.TeamRequest;
import com.fulvo.backend.dto.team.TeamResponse;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final ScoreboardService scoreboardService;
    private final ParticipationService participationService;

    public GenericResponse createTeam(TeamRequest request) {
        User captain = userService.getUser();

        boolean exist = teamRepository.existsByNameAndCaptain(request.getName(), captain);
        if(exist)
            throw new RuntimeException("Ya tenes un equipo con ese nombre");

        Team team = Team.builder()
                .name(request.getName())
                .captain(userService.getUser())
                .build();

        teamRepository.save(team);
        return GenericResponse.builder()
                .name("Equipo creado")
                .message(team.getName())
                .build();
    }

    public GenericResponse deleteTeam(TeamRequest request) {
        User captain = userService.getUser();
        Team team = teamRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el equipo"));
        if (captain != team.getCaptain())
            throw new RuntimeException("No sos el capitán del equipo");

        List<Scoreboard> scoreboardList = scoreboardService.findAllByTeam(team);
        if (!scoreboardList.isEmpty()){
            scoreboardService.deleteAll(scoreboardList);
        }

        teamRepository.deleteById(team.getId());
        return GenericResponse.builder()
                .name("Equipo eliminado")
                .message(request.getName())
                .build();
    }

    public Team authTeam(Integer id){
        Team team = getTeam(id);
        User admin = userService.getUser();
        if (admin != team.getCaptain()){
            throw new RuntimeException("No sos el administrador del torneo");
        }
        return team;
    }

    public Team getTeam(Integer id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    public TeamResponse getTeamResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .captain_name(team.getCaptain().getLastName() + ", " + team.getCaptain().getFirstName())
                .build();
    }

    public List<TeamResponse> getMyTeams() {
        User captain = userService.getUser();
        List<Team> teams = teamRepository.findByCaptain(captain)
                .orElseThrow(() -> new RuntimeException("No tenés equipos"));
        return teams.stream()
                .map(this::getTeamResponse)
                .collect(Collectors.toList());
    }

    public TeamResponse selectTeam(TeamRequest request) {
        Team team = getTeam(request.getId());
        User captain = userService.getUser();
        if (captain != team.getCaptain()){
            throw new RuntimeException("No sos el capitan del equipo");
        }
        return getTeamResponse(team);
    }

    private List<TeamResponse> getAllTeamsResponses(List<Team> teams) {
        return teams.stream()
                .map(this::getTeamResponse)
                .collect(Collectors.toList());
    }

    public GenericResponse acceptInvitation(RegistrationDTO request){
        Team team = authTeam(request.getTeamId());
        TournamentInvitation invitation = participationService.findInvitationById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la solicitud"));

        invitation.setStatus(StatusParticipation.ACCEPT);
        participationService.save(invitation);
        return scoreboardService.joinTournament(team, invitation.getTournament());
    }

    public GenericResponse declineInvitation(RegistrationDTO request){
        Team team = authTeam(request.getTeamId());
        TournamentInvitation invitation = participationService.findInvitationById(request.getId())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la solicitud"));

        invitation.setStatus(StatusParticipation.DECLINE);
        participationService.save(invitation);
        return GenericResponse.builder()
                .name("Invitación rechazada")
                .build();
    }

    public List<Team> getAllTeams(List<TournamentRegistration> registrations) {
        List<Team> teams = new ArrayList<>();
        for(TournamentRegistration r : registrations){
            teams.add(r.getTeam());
        }
        return teams;
    }
}
