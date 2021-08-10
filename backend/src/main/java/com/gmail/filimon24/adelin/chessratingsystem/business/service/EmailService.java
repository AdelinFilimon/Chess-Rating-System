package com.gmail.filimon24.adelin.chessratingsystem.business.service;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameResultResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameInvitation;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Constants.MAIL_SENDER_USERNAME);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendGameRequestEmail(GameInvitation gameInvitation) {
        String subject = "Chess Rating System - Game Invitation by " + gameInvitation.getSender().getUsername();
        String to = gameInvitation.getReceiver().getEmail();
        String text = "You are being invited to play a game by user " +
                gameInvitation.getSender().getUsername() +
                "\nInvitation sent on " +
                gameInvitation.getDate();
        sendSimpleMessage(to, subject, text);
    }

    public void sendGameResult(GameResultResponse gameResultResponse, String opponentUsername, String to) {
        String res;
        String currentDate = DateTime.now().toString(DateTimeFormat.forPattern(Constants.DATETIME_FORMAT));
        if (gameResultResponse.getGameResultState() == GameResultState.DRAW) res = "drew";
        else
            res = gameResultResponse.getGameResultState() == GameResultState.WHITE_PIECES_PLAYER_WINS ? "won" : "lose";

        String subject = "Chess Rating System - You " + res + " against " + opponentUsername;
        String stringBuilder = "Game played on " + currentDate +
                "\nNew rating: " + gameResultResponse.getNewRating();

        sendSimpleMessage(to, subject, stringBuilder);

    }

}
