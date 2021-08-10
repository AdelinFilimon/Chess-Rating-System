package com.gmail.filimon24.adelin.chessratingsystem.business.ranking;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.UserRankResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankService {

    public List<UserRankResponse> buildRank(List<User> users) {
        List<UserRankResponse> rank = users.stream()
                .map((user) -> UserRankResponse.builder()
                        .rating(user.getRating())
                        .username(user.getUsername())
                        .build())
                .sorted((userRank1, userRank2) -> userRank2.getRating() - userRank1.getRating())
                .collect(Collectors.toList());

        for (int i = 0; i < rank.size(); i++) rank.get(i).setRank(i);

        return rank;
    }

}
