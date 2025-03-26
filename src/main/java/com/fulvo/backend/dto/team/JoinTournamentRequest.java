package com.fulvo.backend.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinTournamentRequest {
    private Integer teamId;
    private Integer tournamentId;
}
