package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    List<Tournament> findAllByAdminId(int adminId);
    void deleteAllByAdminId(int adminId);
}
