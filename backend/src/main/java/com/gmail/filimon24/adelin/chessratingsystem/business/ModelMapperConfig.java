package com.gmail.filimon24.adelin.chessratingsystem.business;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.Country;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(userUserEntityMap());
        modelMapper.addMappings(userEntityUserMap());
        return modelMapper;
    }

    private PropertyMap<User, UserEntity> userUserEntityMap() {
        Converter<String, DateTime> stringDateTimeConverter =  new AbstractConverter<String, DateTime>() {
            @Override
            protected DateTime convert(String s) {
                if (s == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.BIRTHDAY_FORMAT);
                return formatter.parseDateTime(s);
            }
        };

        Converter<String, Country> stringCountryConverter = new AbstractConverter<String, Country>() {
            @Override
            protected Country convert(String s) {
                if (s == null) return null;
                return Arrays.stream(Country.values())
                        .filter(c -> c.getCode().equals(s))
                        .collect(Collectors.toList()).get(0);
            }
        };

        return new PropertyMap<User, UserEntity>() {
            @Override
            protected void configure() {
                using(stringDateTimeConverter).map(source.getBirthday()).setBirthday(null);
                using(stringCountryConverter).map(source.getCountry()).setCountry(null);
            }
        };
    }

    private PropertyMap<UserEntity, User> userEntityUserMap() {
        Converter<DateTime, String> dateTimeStringConverter = new AbstractConverter<DateTime, String>() {
            @Override
            protected String convert(DateTime dateTime) {
                if (dateTime == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.BIRTHDAY_FORMAT);
                return dateTime.toString(formatter);
            }
        };

        Converter<Country, String > countryStringConverter = new AbstractConverter<Country, String>() {
            @Override
            protected String convert(Country country) {
                if (country == null) return null;
                return country.getCode();
            }
        };

        return new PropertyMap<UserEntity, User>() {
            @Override
            protected void configure() {
                using(dateTimeStringConverter).map(source.getBirthday()).setBirthday(null);
                using(countryStringConverter).map(source.getCountry()).setCountry(null);
            }
        };
    }

}
