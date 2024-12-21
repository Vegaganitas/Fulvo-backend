package com.fulvo.backend.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "matches", schema = "main")
public class Match {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    @Column(name = "result")
    private String result;
    @Column(name = "date")
    private Date date;
    @Column(name = "round")
    private int round;

    @ManyToOne
    @JoinColumn(name = "mvp", referencedColumnName = "id")
    private User mvp; // ESTO VA A SER UN PLAYER

    public int getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        if (tournament != null) {
            this.tournament = tournament;
        }
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        if (homeTeam != null) {
            this.homeTeam = homeTeam;
        }
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        if (awayTeam != null) {
            this.awayTeam = awayTeam;
        }

    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        if (homeTeamScore >= 0) {
            this.homeTeamScore = homeTeamScore;
        }
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        if (awayTeamScore >= 0) {
            this.awayTeamScore = awayTeamScore;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = getHomeTeamScore() + " - " + getAwayTeamScore();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date != null) {
            this.date = date;
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        if (round > 0) {
            this.round = round;
        }
    }

    public User getMvp() {
        return mvp;
    }

    public void setMvp(User mvp) {
        if (mvp != null) {
            this.mvp = mvp;
        }

    }
}
