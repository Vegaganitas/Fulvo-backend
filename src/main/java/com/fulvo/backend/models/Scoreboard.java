package com.fulvo.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Scoreboard", schema = "public")
public class Scoreboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    private Integer points;
    private Integer gamesPlayed;
    private Integer wins;
    private Integer draws;
    private Integer loses;
    private Integer goalsFor;
    private Integer goalsAgainst;


    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void addPoints(int i) {
        this.points += i;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementDraws() {
        this.draws++;
    }

    public void incrementLosses() {
        this.loses++;
    }
}
