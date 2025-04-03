package com.fulvo.backend.repositories;

import com.fulvo.backend.models.TournamentInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<TournamentInvitation, Integer> {
}
