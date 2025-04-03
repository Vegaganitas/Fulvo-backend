package com.fulvo.backend.services;

import com.fulvo.backend.dto.GenericResponse;
import com.fulvo.backend.dto.registration.RegistrationDTO;
import com.fulvo.backend.models.*;
import com.fulvo.backend.repositories.InvitationRepository;
import com.fulvo.backend.repositories.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final RegistrationRepository registrationRepository;
    private final InvitationRepository invitationRepository;

    public GenericResponse invitation(Team team, Tournament tournament) {
        TournamentInvitation invitation = TournamentInvitation.builder()
                .team(team)
                .tournament(tournament)
                .status(StatusParticipation.PENDING)
                .build();
        save(invitation);
        return GenericResponse.builder()
                .name("Invitación enviada")
                .message("Se ha invitado a " + team.getName())
                .build();
    }

    public GenericResponse registration(Team team, Tournament tournament) {
        TournamentRegistration registration = TournamentRegistration.builder()
                .team(team)
                .tournament(tournament)
                .status(StatusParticipation.PENDING)
                .build();
        save(registration);
        return GenericResponse.builder()
                .name("Solicitud enviada")
                .message("Se ha solicitado la inscripción a " + tournament.getName())
                .build();
            }

    public Optional<TournamentRegistration> findRegistrationById(Integer request) {
        return registrationRepository.findById(request);
    }

    public Optional<TournamentInvitation> findInvitationById(Integer request) {
        return invitationRepository.findById(request);
    }

    public void save(TournamentRegistration participation) {
        registrationRepository.save(participation);
    }

    public void save(TournamentInvitation participation) {
        invitationRepository.save(participation);
    }

    public List<TournamentRegistration> findRegistrationsPending(Tournament tournament, StatusParticipation statusParticipation) {
        return registrationRepository.findByTournamentAndStatus(tournament, statusParticipation);
    }

    public RegistrationDTO getRegistrationResponse(TournamentRegistration t) {
        return RegistrationDTO.builder()
                .id(t.getId())
                .teamId(t.getTeam().getId())
                .teamName(t.getTeam().getName())
                .tournamentId(t.getTournament().getId())
                .tournamentName(t.getTournament().getName())
                .status(t.getStatus())
                .build();
    }
}
