package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Team;
import com.fulvo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findByNameAndCaptainId(String name, Integer captainId);
}
