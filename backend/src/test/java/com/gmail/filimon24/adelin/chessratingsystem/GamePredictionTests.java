package com.gmail.filimon24.adelin.chessratingsystem;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.PairwiseComparisonStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class GamePredictionTests {

    private final PairwiseComparisonStrategy predictionStrategy = new PairwiseComparisonStrategy();

    @Test
    public void higherRatingPlayerShouldWin() {
        User user1 = User.builder().rating(1250).build();
        User user2 = User.builder().rating(800).build();
        assertThat(GameResultState.WHITE_PIECES_PLAYER_WINS).isEqualTo(predictionStrategy.predictGame(user1, user2).getGameResultState());
    }

    @Test
    public void closerRatingsShouldResultInDraws() {
        User user1 = User.builder().rating(1100).build();
        User user2 = User.builder().rating(1000).build();

        int draws = 0;

        for (int i = 0; i < 10; i++) {
            GameResultState state = predictionStrategy.predictGame(user1, user2).getGameResultState();
            System.out.println(state);
            if (state == GameResultState.DRAW)
                draws ++;
        }
        assertThat(draws).isNotEqualTo(0);
    }

}
