package com.gmail.filimon24.adelin.chessratingsystem.business;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.business.model.User;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.Country;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
        Converter<String, LocalDate> stringLocalDateConverter =  new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String s) {
                if (s == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.BIRTHDAY_FORMAT);
                return formatter.parseLocalDate(s);
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
                using(stringLocalDateConverter).map(source.getBirthday()).setBirthday(null);
                using(stringCountryConverter).map(source.getCountry()).setCountry(null);
            }
        };
    }

    private PropertyMap<UserEntity, User> userEntityUserMap() {
        Converter<LocalDate, String> dateTimeStringConverter = new AbstractConverter<LocalDate, String>() {
            @Override
            protected String convert(LocalDate localDate) {
                if (localDate == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.BIRTHDAY_FORMAT);
                return localDate.toString(formatter);
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
