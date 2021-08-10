package com.gmail.filimon24.adelin.chessratingsystem.business.config;

import com.gmail.filimon24.adelin.chessratingsystem.business.dto.GameInvitationResponse;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameHistory;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.GameInvitation;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameHistoryEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.GameInvitationEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final CustomConverters customConverters;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(userUserEntityMap());
        modelMapper.addMappings(userEntityUserMap());
        modelMapper.addMappings(gameInvitationGameInvitationEntityMap());
        modelMapper.addMappings(gameInvitationEntityGameInvitationMap());
        modelMapper.addMappings(gameInvitationGameInvitationResponseMap());
        modelMapper.addMappings(gameHistoryGameHistoryEntityMap());
        modelMapper.addMappings(gameHistoryEntityGameHistoryMap());
        return modelMapper;
    }

    private PropertyMap<User, UserEntity> userUserEntityMap() {

        return new PropertyMap<User, UserEntity>() {
            @Override
            protected void configure() {
                using(customConverters.getStringToLocalDateConverters()).map(source.getDateOfBirth()).setDateOfBirth(null);
                using(customConverters.getStringToCountryFieldConverter()).map(source.getCountry()).setCountry(null);
            }
        };
    }

    private PropertyMap<UserEntity, User> userEntityUserMap() {
        return new PropertyMap<UserEntity, User>() {
            @Override
            protected void configure() {
                using(customConverters.getLocalDateToStringConverter()).map(source.getDateOfBirth()).setDateOfBirth(null);
                using(customConverters.getCountryFieldToStringConverter()).map(source.getCountry()).setCountry(null);
            }
        };
    }

    private PropertyMap<GameInvitation, GameInvitationEntity> gameInvitationGameInvitationEntityMap() {
        return new PropertyMap<GameInvitation, GameInvitationEntity>() {
            @Override
            protected void configure() {
                using(customConverters.getStringToDateTimeConverter()).map(source.getDate()).setDate(null);
                using(customConverters.getStringToGameRequestStateConverter()).map(source.getRequestState()).setRequestState(null);
            }
        };
    }

    private PropertyMap<GameInvitationEntity, GameInvitation> gameInvitationEntityGameInvitationMap() {
        return new PropertyMap<GameInvitationEntity, GameInvitation>() {
            @Override
            protected void configure() {
                using(customConverters.getDateTimeToStringConverter()).map(source.getDate()).setDate(null);
                using(customConverters.getGameRequestStateToStringConverter()).map(source.getRequestState()).setRequestState(null);
            }
        };
    }

    private PropertyMap<GameInvitation, GameInvitationResponse> gameInvitationGameInvitationResponseMap() {
        return new PropertyMap<GameInvitation, GameInvitationResponse>() {
            @Override
            protected void configure() {
                map().setSenderUsername(source.getSender().getUsername());
                map().setSenderEmail(source.getSender().getEmail());
                map().setReceiverUsername(source.getReceiver().getUsername());
                map().setReceiverEmail(source.getReceiver().getEmail());
                map().setDateOfSending(source.getDate());
                map().setStatus(source.getRequestState());
            }
        };
    }

    private PropertyMap<GameHistory, GameHistoryEntity> gameHistoryGameHistoryEntityMap() {
        return new PropertyMap<GameHistory, GameHistoryEntity>() {
            @Override
            protected void configure() {
                using(customConverters.getStringToDateTimeConverter()).map(source.getStartingDate()).setStartingDate(null);
            }
        };

    }

    private PropertyMap<GameHistoryEntity, GameHistory> gameHistoryEntityGameHistoryMap() {
        return new PropertyMap<GameHistoryEntity, GameHistory>() {
            @Override
            protected void configure() {
                using(customConverters.getDateTimeToStringConverter()).map(source.getStartingDate()).setStartingDate(null);
            }
        };
    }

}
