package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankResponse {
    private Integer rank;
    private String username;
    private Integer rating;
}
