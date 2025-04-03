package com.fulvo.backend.dto.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {
    private Integer id;
    private Integer homeTeam;
    private Integer homeGoals;
    private Integer awayTeam;
    private Integer awayGoals;
    private Integer date;
    private Date day;
}
