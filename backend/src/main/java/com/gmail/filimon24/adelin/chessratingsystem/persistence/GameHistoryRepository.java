package com.gmail.filimon24.adelin.chessratingsystem.persistence;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistoryEntity, Long> {
    List<GameHistoryEntity> findAllByBlackPiecesPlayerUsernameOrBlackPiecesPlayerUsername(String username1, String username2);
}
