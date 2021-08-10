package com.gmail.filimon24.adelin.chessratingsystem.business.dto;

import lombok.Data;

@Data
public class GameInvitationResponse {
    private Long id;
    private String senderUsername;
    private String senderEmail;
    private String receiverUsername;
    private String receiverEmail;
    private String dateOfSending;
    private String status;
}
