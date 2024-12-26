package com.fulvo.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "teams", schema = "main")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "captain_id", referencedColumnName = "id")
    private User captain;

//    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Scoreboard> scoreboards =  new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCaptain() {
        return captain;
    }

    public void setCaptain(User captain) {
        this.captain = captain;
    }

//    public List<Scoreboard> getScoreboards() {
//        return scoreboards;
//    }
//
//    public void addScoreboard(Scoreboard scoreboard) {
//        this.scoreboards.add(scoreboard);
//        scoreboard.setTeam(this);
//    }
//
//    public void removeScoreboard(Scoreboard scoreboard) {
//        this.scoreboards.remove(scoreboard);
//        scoreboard.setTeam(null);
//    }

}
