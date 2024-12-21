package com.fulvo.backend.services;

import com.fulvo.backend.models.League;
import com.fulvo.backend.repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    // Obtener todas las ligas
    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    // Obtener una liga por su id
    public League getLeagueById(int id) {
        return leagueRepository.findById(id).orElse(null);
    }

    // Crear una liga
    public League createLeague(League league) {
        return leagueRepository.save(league);
    }

    // Eliminar una liga
    public void deleteLeague(int id) {
        leagueRepository.deleteById(id);
    }

}
