package com.fulvo.backend.repositories;

import com.fulvo.backend.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    void deleteByCaptainId(int id);
    List<Team> findByCaptainId(int id);
}
