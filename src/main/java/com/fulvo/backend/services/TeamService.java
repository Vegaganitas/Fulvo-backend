package com.fulvo.backend.services;


import com.fulvo.backend.models.Team;
import com.fulvo.backend.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Crear un nuevo equipo
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Eliminar un equipo por ID
    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }
}
