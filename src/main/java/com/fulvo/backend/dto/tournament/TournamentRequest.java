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
    String name;
    User admin;
}
