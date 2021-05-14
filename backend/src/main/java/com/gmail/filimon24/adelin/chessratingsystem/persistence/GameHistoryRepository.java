package com.gmail.filimon24.adelin.chessratingsystem.persistence;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistoryEntity, Long> {
}
