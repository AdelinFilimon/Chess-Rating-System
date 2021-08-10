package com.gmail.filimon24.adelin.chessratingsystem.business.service.util;

import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerDrawObserver extends Observer {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PlayerDrawObserver(UserGameStatisticsManager userGameStatisticsManager, UserRepository userRepository, ModelMapper modelMapper) {
        this.userGameStatisticsManager = userGameStatisticsManager;
        this.userGameStatisticsManager.attach(this);
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void update(GamePredictionResult state) {
        if (state.getGameResultState() != GameResultState.DRAW) return;

        User user1 = state.getWhitePiecesPlayer();
        User user2 = state.getBlackPiecesPlayer();

        user1.setRating(state.getWhitePiecesPlayerNewRating());
        user2.setRating(state.getBlackPiecesPlayerNewRating());
        user1.setNrOfDraws(user1.getNrOfDraws() + 1);
        user2.setNrOfDraws(user2.getNrOfDraws() + 1);
        userRepository.save(modelMapper.map(user1, UserEntity.class));
        userRepository.save(modelMapper.map(user2, UserEntity.class));
    }
}

