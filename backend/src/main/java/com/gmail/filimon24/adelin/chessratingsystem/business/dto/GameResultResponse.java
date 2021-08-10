package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResultResponse {
    private GameResultState gameResultState;
    private String winner;
    private String looser;
    private Integer newRating;
}
