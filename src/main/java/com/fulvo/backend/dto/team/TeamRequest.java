package com.fulvo.backend.dto.team;

import com.fulvo.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequest {
    String name;
    User captain_id;
}
