package com.gmail.filimon24.adelin.chessratingsystem.business.config;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.PairwiseComparisonStrategy;
import com.gmail.filimon24.adelin.chessratingsystem.business.ranking.PredictionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PredictionConfig {

    @Bean
    public PredictionStrategy predictionStrategy() {
        switch (Constants.PREDICTION_STRATEGY) {
            case PAIRWISE_COMPARISON:
                return new PairwiseComparisonStrategy();
            default:
                return new PairwiseComparisonStrategy();
        }
    }
}
