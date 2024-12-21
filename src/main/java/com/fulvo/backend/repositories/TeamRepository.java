package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    void deleteByCaptainId(int id);
    List<Team> findByCaptainId(int id);
}
