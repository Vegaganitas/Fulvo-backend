package com.fulvo.backend.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "scoreboard", schema = "main")
public class Scoreboard {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Column(name = "team_group")
    private String group;
    @Column(name = "points")
    private int points;
    @Column(name = "games_played")
    private int gamesPlayed;
    @Column(name = "games_won")
    private int gamesWon;
    @Column(name = "games_lost")
    private int gamesLost;
    @Column(name = "games_drawn")
    private int gamesDrawn;
    @Column(name = "goals_for")
    private int goalsFor;
    @Column(name = "goals_against")
    private int goalsAgainst;

    public int getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = 0;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = 0;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = 0;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = 0;
    }

    public int getGamesDrawn() {
        return gamesDrawn;
    }

    public void setGamesDrawn(int gamesDrawn) {
        this.gamesDrawn = 0;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = 0;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = 0;
    }
}
