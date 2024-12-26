package com.fulvo.backend.services;


import com.fulvo.backend.models.Team;
import com.fulvo.backend.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Obtener todos los equipos
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Obtener un equipo por ID
    public Team getTeamById(int id) {
        return teamRepository.findById(id).orElse(null);
    }

    // Obtener todos los equipos de un capitán
    public List<Team> getTeamsByCaptain(int id) {
        return teamRepository.findByCaptainId(id);
    }

    // Crear un nuevo equipo
    @Transactional
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Eliminar un equipo por ID
    @Transactional
    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }

    // Eliminar todos los equipos de un capitán
    @Transactional
    public List<Team> deleteTeamByCaptain(int id) {
        List<Team> teamsToDelete = teamRepository.findByCaptainId(id);
        teamRepository.deleteByCaptainId(id);
        return teamsToDelete;
    }

}
