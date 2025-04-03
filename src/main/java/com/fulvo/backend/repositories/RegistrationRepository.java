package com.fulvo.backend.repositories;


import com.fulvo.backend.models.StatusParticipation;
import com.fulvo.backend.models.Tournament;
import com.fulvo.backend.models.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<TournamentRegistration, Integer> {
    List<TournamentRegistration> findByTournamentAndStatus(Tournament tournament, StatusParticipation statusParticipation);
}
