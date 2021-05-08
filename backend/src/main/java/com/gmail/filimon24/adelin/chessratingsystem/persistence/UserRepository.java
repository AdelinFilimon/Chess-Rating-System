package com.gmail.filimon24.adelin.chessratingsystem.persistence;

import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<User, Long> {
}