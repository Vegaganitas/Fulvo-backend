package com.fulvo.backend.tournaments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    List<Tournament> findAllByAdminId(int adminId);
    void deleteAllByAdminId(int adminId);
}
