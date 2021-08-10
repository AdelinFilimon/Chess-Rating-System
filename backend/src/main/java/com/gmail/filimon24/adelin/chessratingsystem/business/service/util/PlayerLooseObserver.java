package com.gmail.filimon24.adelin.chessratingsystem.business.service.util;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class PlayerLooseObserver extends Observer{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PlayerLooseObserver(UserGameStatisticsManager userGameStatisticsManager, UserRepository userRepository, ModelMapper modelMapper) {
        this.userGameStatisticsManager = userGameStatisticsManager;
        this.userGameStatisticsManager.attach(this);
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void update(GamePredictionResult state) {
        if (state.getGameResultState() == GameResultState.DRAW) return;

        boolean isLooserWhiteColour = state.getGameResultState() == GameResultState.BLACK_PIECES_PLAYER_WINS;

        User user = isLooserWhiteColour ? state.getWhitePiecesPlayer() : state.getBlackPiecesPlayer();
        user.setRating(isLooserWhiteColour ? state.getWhitePiecesPlayerNewRating() : state.getBlackPiecesPlayerNewRating());
        user.setNrOfLosses(user.getNrOfLosses() + 1);
        userRepository.save(modelMapper.map(user, UserEntity.class));
    }

}

