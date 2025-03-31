package com.fulvo.backend.dto.match;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateResponse {
    private List<MatchResponse> matches;
    private Integer date;
}
