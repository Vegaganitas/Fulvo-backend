package com.fulvo.backend.dto.registration;

import com.fulvo.backend.models.StatusParticipation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO {
    Integer id;
    String tournamentName;
    Integer tournamentId;
    String teamName;
    Integer teamId;
    StatusParticipation status;
    String message;
}
