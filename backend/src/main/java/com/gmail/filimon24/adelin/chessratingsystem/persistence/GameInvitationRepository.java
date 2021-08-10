package com.gmail.filimon24.adelin.chessratingsystem.persistence;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameInvitationRepository extends JpaRepository<GameInvitationEntity, Long> {
    List<GameInvitationEntity> getAllBySenderUsername(String senderUsername);
    List<GameInvitationEntity> getAllByReceiverUsername(String receiverUsername);
    List<GameInvitationEntity> getBySenderUsernameAndReceiverUsername(String senderUsername, String receiverUsername);
}
