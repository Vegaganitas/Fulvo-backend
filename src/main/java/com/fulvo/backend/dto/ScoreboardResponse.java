package com.fulvo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreboardResponse {
    Integer id;
    Integer teamId;
    String teamName;
    Integer points;
    Integer gamesPlayed;
    Integer wins;
    Integer draws;
    Integer loses;
    Integer goalsFor;
    Integer goalsAgainst;
}
