package com.gmail.filimon24.adelin.chessratingsystem.business.service.util;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerWinObserver extends Observer {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PlayerWinObserver(UserGameStatisticsManager userGameStatisticsManager, UserRepository userRepository, ModelMapper modelMapper) {
        this.userGameStatisticsManager = userGameStatisticsManager;
        this.userGameStatisticsManager.attach(this);
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void update(GamePredictionResult state) {
        if (state.getGameResultState() == GameResultState.DRAW) return;

        boolean isWinnerWhiteColour = state.getGameResultState() == GameResultState.WHITE_PIECES_PLAYER_WINS;

        User user = isWinnerWhiteColour ? state.getWhitePiecesPlayer() : state.getBlackPiecesPlayer();
        user.setRating(isWinnerWhiteColour ? state.getWhitePiecesPlayerNewRating() : state.getBlackPiecesPlayerNewRating());
        user.setNrOfWins(user.getNrOfWins() + 1);

        userRepository.save(modelMapper.map(user, UserEntity.class));
    }
}
