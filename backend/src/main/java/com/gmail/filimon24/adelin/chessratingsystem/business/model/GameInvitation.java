package com.gmail.filimon24.adelin.chessratingsystem.business.model;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameRequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInvitation {
    private Long id;
    private User sender;
    private User receiver;
    private String date;
    private String requestState;

    public static GameInvitation buildInvitation(User sender, User receiver) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(Constants.DATETIME_FORMAT);
        return GameInvitation.builder()
                .sender(sender)
                .receiver(receiver)
                .date(DateTime.now().toString(dateTimeFormatter))
                .requestState(GameRequestState.PENDING.getCode())
                .build();
    }
}
