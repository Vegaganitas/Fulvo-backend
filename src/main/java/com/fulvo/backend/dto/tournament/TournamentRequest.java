package com.fulvo.backend.dto.tournament;

import com.fulvo.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentRequest {
    Integer id;
    String name;
    Integer teamsMax;
    Integer teamsMin;
    Boolean roundTrip;
    User admin;
}
