package com.gmail.filimon24.adelin.chessratingsystem.business.service;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameHistoryResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.exception.GameInvitationNotFoundException;
import com.gmail.filimon24.adelin.chessratingsystem.business.exception.UserNotFoundException;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameHistory;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameInvitation;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.PredictionStrategy;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.util.UserGameStatisticsManager;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.GameHistoryRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.GameInvitationRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.UserRepository;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameHistoryEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameInvitationEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameRequestState;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameInvitationRepository gameInvitationRepository;
    private final UserGameStatisticsManager userGameStatisticsManager;
    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PredictionStrategy predictionStrategy;

    public GameInvitation insertGameInvitation(User sender, User receiver) {
        GameInvitation gameInvitation = GameInvitation.buildInvitation(sender, receiver);
        return modelMapper.map(gameInvitationRepository.save(modelMapper.map(gameInvitation, GameInvitationEntity.class)),
                GameInvitation.class);
    }

    public List<GameInvitation> getInvitesBySenderUsername(String senderUsername) {
        return gameInvitationRepository.getAllBySenderUsername(senderUsername)
                .stream()
                .map((gameInvitation) -> modelMapper.map(gameInvitation, GameInvitation.class))
                .collect(Collectors.toList());
    }

    public List<GameInvitation> getInvitesByReceiverUsername(String receiverUsername) {
        return gameInvitationRepository.getAllByReceiverUsername(receiverUsername)
                .stream()
                .map((gameInvitation) -> modelMapper.map(gameInvitation, GameInvitation.class))
                .collect(Collectors.toList());
    }

    public GameInvitation getInviteBySenderAndReceiver(String senderUsername, String receiverUsername) {
        List<GameInvitationEntity> invites = gameInvitationRepository.getBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);

        if (invites.size() == 0)
            throw new GameInvitationNotFoundException();

        for (GameInvitationEntity gameInvitationEntity : invites) {
            if (gameInvitationEntity.getRequestState() == GameRequestState.PENDING)
                return modelMapper.map(gameInvitationEntity, GameInvitation.class);
        }

        throw new GameInvitationNotFoundException();
    }

    public GamePredictionResult acceptInvitation(GameInvitation gameInvitation) {
        gameInvitation.setRequestState(GameRequestState.ACCEPTED.getCode());
        gameInvitationRepository.save(modelMapper.map(gameInvitation, GameInvitationEntity.class));

        GamePredictionResult gamePredictionResult = predictionStrategy.predictGame(gameInvitation.getSender(), gameInvitation.getReceiver());

        User sender = gameInvitation.getSender();
        User receiver = gameInvitation.getReceiver();

        gamePredictionResult.setWhitePiecesPlayer(sender);
        gamePredictionResult.setBlackPiecesPlayer(receiver);

        userGameStatisticsManager.setState(gamePredictionResult);

        GameHistory gameHistory = GameHistory.builder()
                .startingDate(gameInvitation.getDate())
                .whitePiecesPlayer(sender)
                .blackPiecesPlayer(receiver)
                .gameOutcome(gamePredictionResult.getGameResultState().name())
                .build();

        gameHistoryRepository.save(modelMapper.map(gameHistory, GameHistoryEntity.class));

        return gamePredictionResult;
    }

    public void refuseInvitation(GameInvitation gameInvitation) {
        gameInvitation.setRequestState(GameRequestState.REFUSED.getCode());
        gameInvitationRepository.save(modelMapper.map(gameInvitation, GameInvitationEntity.class));
    }

    public List<GameHistoryResponse> getPlayedGamesByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        List<GameHistoryEntity> games = new ArrayList<>();
        games.addAll(userEntity.getWhitePiecesGames());
        games.addAll(userEntity.getBlackPiecesGames());
        return games.stream()
                .map((game) -> modelMapper.map(game, GameHistory.class))
                .map((game) ->
                    GameHistoryResponse.builder()
                            .whitePiecesPlayerUsername(game.getWhitePiecesPlayer().getUsername())
                            .blackPiecesPlayerUsername(game.getBlackPiecesPlayer().getUsername())
                            .whitePiecesPlayerRating(game.getWhitePiecesPlayer().getRating())
                            .blackPiecesPlayerRating(game.getBlackPiecesPlayer().getRating())
                            .outcome(game.getGameOutcome())
                            .build()
                )
                .collect(Collectors.toList());
    }
}
