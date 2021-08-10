package com.gmail.filimon24.adelin.chessratingsystem.business.config;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.CountryField;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.util.GameRequestState;
import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Component
public class CustomConverters {
    private final Converter<String, LocalDate> stringToLocalDateConverters;
    private final Converter<LocalDate, String> localDateToStringConverter;
    private final Converter<CountryField, String> countryFieldToStringConverter;
    private final Converter<String, CountryField> stringToCountryFieldConverter;
    private final Converter<String, DateTime> stringToDateTimeConverter;
    private final Converter<DateTime, String> dateTimeToStringConverter;
    private final Converter<String, GameRequestState> stringToGameRequestStateConverter;
    private final Converter<GameRequestState, String> gameRequestStateToStringConverter;

    private Converter<LocalDate, String> createLocalDateToStringConverter() {
        return new AbstractConverter<org.joda.time.LocalDate, String>() {
            @Override
            protected String convert(org.joda.time.LocalDate localDate) {
                if (localDate == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_OF_BIRTH_FORMAT);
                return localDate.toString(formatter);
            }
        };
    }

    private Converter<CountryField, String> createCountryFieldToStringConverter() {
        return new AbstractConverter<CountryField, String>() {
            @Override
            protected String convert(CountryField country) {
                if (country == null) return null;
                return country.getCode();
            }
        };
    }

    private Converter<String, LocalDate> createStringToLocalDateConverter() {
        return new AbstractConverter<String, org.joda.time.LocalDate>() {
            @Override
            protected org.joda.time.LocalDate convert(String s) {
                if (s == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_OF_BIRTH_FORMAT);
                return formatter.parseLocalDate(s);
            }
        };
    }

    private Converter<String, CountryField> createStringToCountryFieldConverter() {
        return new AbstractConverter<String, CountryField>() {
            @Override
            protected CountryField convert(String s) {
                if (s == null) return null;
                return Arrays.stream(CountryField.values())
                        .filter(c -> c.getCode().equals(s))
                        .collect(Collectors.toList()).get(0);
            }
        };
    }

    private Converter<String, DateTime> createStringToDateTimeConverter() {
        return new AbstractConverter<String, DateTime>() {
            @Override
            protected DateTime convert(String s) {
                if (s == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATETIME_FORMAT);
                return formatter.parseDateTime(s);
            }
        };
    }

    private Converter<DateTime, String> createDateTimeToStringConverter() {
        return new AbstractConverter<DateTime, String>() {
            @Override
            protected String convert(DateTime dateTime) {
                if (dateTime == null) return null;
                DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATETIME_FORMAT);
                return dateTime.toString(formatter);
            }
        };
    }

    private Converter<String, GameRequestState> createStringToGameRequestStateConverter() {
        return new AbstractConverter<String, GameRequestState>() {
            @Override
            protected GameRequestState convert(String s) {
                if (s == null) return null;
                return Arrays.stream(GameRequestState.values())
                        .filter(c -> c.getCode().equals(s))
                        .collect(Collectors.toList()).get(0);
            }
        };
    }

    private Converter<GameRequestState, String> createGameRequestStateToStringConverter() {
        return new AbstractConverter<GameRequestState, String>() {
            @Override
            protected String convert(GameRequestState state) {
                if (state == null) return null;
                return state.getCode();
            }
        };
    }

    public CustomConverters() {
        this.countryFieldToStringConverter = createCountryFieldToStringConverter();
        this.localDateToStringConverter = createLocalDateToStringConverter();
        this.stringToCountryFieldConverter = createStringToCountryFieldConverter();
        this.stringToLocalDateConverters = createStringToLocalDateConverter();
        this.stringToDateTimeConverter = createStringToDateTimeConverter();
        this.dateTimeToStringConverter = createDateTimeToStringConverter();
        this.stringToGameRequestStateConverter = createStringToGameRequestStateConverter();
        this.gameRequestStateToStringConverter = createGameRequestStateToStringConverter();
    }

}
