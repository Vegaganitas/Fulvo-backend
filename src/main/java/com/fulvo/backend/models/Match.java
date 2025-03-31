package com.fulvo.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Match", schema = "public")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Scoreboard homeTeam;
    private Integer homeGoals;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Scoreboard awayTeam;
    private Integer awayGoals;

    private Date day;
    private Integer date;

}
