package com.gmail.filimon24.adelin.chessratingsystem.controller;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameInvitationRequest;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameInvitationResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameResultResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameInvitation;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.GamePredictionResult;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameResultState;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.RankService;
import com.gmail.filimon24.adelin.chessratingsystem.business.security.UserDetailsImpl;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.EmailService;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.GameService;
import com.gmail.filimon24.adelin.chessratingsystem.business.service.UserService;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameRequestState;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("game")
@RequiredArgsConstructor
public class GameController {

    private final UserService userService;
    private final GameService gameService;
    private final EmailService emailService;
    private final RankService rankService;
    private final ModelMapper modelMapper;

    @PostMapping("/invite")
    public ResponseEntity<?> createGameRequest(@AuthenticationPrincipal UserDetailsImpl principal, @RequestBody GameInvitationRequest gameInvitationRequest) {
        User sender = userService.findUserByUsername(principal.getUsername());
        User receiver = userService.findUserByGameInvitationRequest(gameInvitationRequest);
        GameInvitation gameInvitation = gameService.insertGameInvitation(sender, receiver);
        emailService.sendGameRequestEmail(gameInvitation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/my-invites")
    public ResponseEntity<?> getMyInvites(@AuthenticationPrincipal UserDetailsImpl principal) {
        List<GameInvitationResponse> invitations = gameService.getInvitesBySenderUsername(principal.getUsername())
                .stream()
                .map((gameInvitation) -> modelMapper.map(gameInvitation, GameInvitationResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }

    @GetMapping("/invites")
    public ResponseEntity<?> getInvites(@AuthenticationPrincipal UserDetailsImpl principal) {
        List<GameInvitationResponse> invitations = gameService.getInvitesByReceiverUsername(principal.getUsername())
                .stream()
                .map((gameInvitation) -> modelMapper.map(gameInvitation, GameInvitationResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }

    @GetMapping("/rank")
    public ResponseEntity<?> getRank() {
        return new ResponseEntity<>(rankService.buildRank(userService.getUsers()), HttpStatus.OK);
    }

    @PostMapping("/invites/{sender}/accept")
    public ResponseEntity<?> acceptGameRequest(@AuthenticationPrincipal UserDetailsImpl principal, @PathVariable String sender) {

        GameInvitation gameInvitation = gameService.getInviteBySenderAndReceiver(sender, principal.getUsername());
        if (!gameInvitation.getRequestState().equals(GameRequestState.PENDING.getCode())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GamePredictionResult result = gameService.acceptInvitation(gameInvitation);

        String winnerUsername = result.getGameResultState() == GameResultState.BLACK_PIECES_PLAYER_WINS ? principal.getUsername() : sender;
        String looserUsername = winnerUsername.equals(principal.getUsername()) ? sender : principal.getUsername();

        GameResultResponse gameResultResponse = GameResultResponse.builder()
                .gameResultState(result.getGameResultState())
                .winner(winnerUsername)
                .looser(looserUsername)
                .newRating(result.getBlackPiecesPlayerNewRating())
                .build();

        GameResultResponse opponentGameResultResponse = GameResultResponse.builder()
                .gameResultState(result.getGameResultState())
                .winner(winnerUsername)
                .looser(looserUsername)
                .newRating(result.getWhitePiecesPlayerNewRating())
                .build();

        emailService.sendGameResult(opponentGameResultResponse, principal.getUsername(), gameInvitation.getSender().getEmail());

        return new ResponseEntity<>(gameResultResponse, HttpStatus.OK);

    }

    @PostMapping("/invites/{sender}/refuse")
    public ResponseEntity<?> refuseGameRequest(@AuthenticationPrincipal UserDetailsImpl principal, @PathVariable String sender) {
        GameInvitation gameInvitation = gameService.getInviteBySenderAndReceiver(sender, principal.getUsername());
        if (!gameInvitation.getRequestState().equals(GameRequestState.PENDING.getCode())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        gameService.refuseInvitation(gameInvitation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getPlayedGames(@AuthenticationPrincipal UserDetailsImpl principal) {
        return new ResponseEntity<>(gameService.getPlayedGamesByUsername(principal.getUsername()), HttpStatus.OK);
    }

}
