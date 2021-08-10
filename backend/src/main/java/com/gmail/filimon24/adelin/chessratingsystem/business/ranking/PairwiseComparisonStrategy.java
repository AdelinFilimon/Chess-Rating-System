package com.gmail.filimon24.adelin.chessratingsystem.business.ranking;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PairwiseComparisonStrategy implements PredictionStrategy {

    @Override
    public GamePredictionResult predictGame(User playerA, User playerB) {
        return predictGame(playerA.getRating(), playerB.getRating(), 30);
    }

    public GamePredictionResult predictGame(Integer whitePiecesPlayerRating, Integer blackPiecesPlayerRating, int K) {

        double qA = Math.pow(10, whitePiecesPlayerRating / 400d);
        double qB = Math.pow(10, blackPiecesPlayerRating / 400d);

        double expectedScorePlayerA = qA / (qA + qB);
        double expectedScorePlayerB = 1 - expectedScorePlayerA;

        boolean playerAScores = Double.compare(expectedScorePlayerA, Math.random()) >= 0;

        if (playerAScores) {
            GameResultState state;
            boolean playerAWins = true;
            if (Double.compare(expectedScorePlayerA, 0.75) < 0) {
                playerAWins = Double.compare(expectedScorePlayerA, Math.random()) < 0;
            }
            int newPlayerARating, newPlayerBRating;
            if (playerAWins) {
                state = GameResultState.WHITE_PIECES_PLAYER_WINS;
                newPlayerARating = (int) (whitePiecesPlayerRating + K * (1 - expectedScorePlayerA));
                newPlayerBRating = (int) (blackPiecesPlayerRating + K * (0 - expectedScorePlayerB));
                newPlayerBRating = Math.max(newPlayerBRating, 0);
            } else {
                state = GameResultState.DRAW;
                newPlayerARating = (int) (whitePiecesPlayerRating + K * (0.5 - expectedScorePlayerA));
                newPlayerBRating = (int) (blackPiecesPlayerRating + K * (0.5 - expectedScorePlayerB));
            }

            return GamePredictionResult.builder()
                    .gameResultState(state)
                    .whitePiecesPlayerNewRating(newPlayerARating)
                    .blackPiecesPlayerNewRating(newPlayerBRating)
                    .build();
        } else {
            GameResultState state;
            boolean playerBWins = true;
            if (Double.compare(expectedScorePlayerB, 0.75) < 0) {
                playerBWins = Double.compare(expectedScorePlayerB, Math.random()) < 0;
            }
            int newPlayerARating, newPlayerBRating;
            if (playerBWins) {
                state = GameResultState.BLACK_PIECES_PLAYER_WINS;
                newPlayerBRating = (int) (blackPiecesPlayerRating + K * (1 - expectedScorePlayerB));
                newPlayerARating = (int) (whitePiecesPlayerRating + K * (0 - expectedScorePlayerA));
                newPlayerARating = Math.max(newPlayerARating, 0);
            } else {
                state= GameResultState.DRAW;
                newPlayerARating = (int) (whitePiecesPlayerRating + K * (0.5 - expectedScorePlayerA));
                newPlayerBRating = (int) (blackPiecesPlayerRating + K * (0.5 - expectedScorePlayerB));
            }

            return GamePredictionResult.builder()
                    .gameResultState(state)
                    .blackPiecesPlayerNewRating(newPlayerBRating)
                    .whitePiecesPlayerNewRating(newPlayerARating)
                    .build();
        }
    }
}
