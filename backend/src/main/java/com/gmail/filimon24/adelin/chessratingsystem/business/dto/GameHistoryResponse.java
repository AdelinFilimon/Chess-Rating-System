package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameHistoryResponse {
    private String whitePiecesPlayerUsername;
    private Integer whitePiecesPlayerRating;
    private String blackPiecesPlayerUsername;
    private Integer blackPiecesPlayerRating;
    private String outcome;
}
