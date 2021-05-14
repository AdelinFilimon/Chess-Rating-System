package com.gmail.filimon24.adelin.chessratingsystem;

public final class Constants {
    public static final int COUNTRY_FIELD_LENGTH = 3;
    public static final int USER_ATTRIBUTE_LENGTH = 48;
    public static final int USER_PASSWORD_LENGTH = 60;
    public static final int USER_STATUS_LENGTH = 128;

    public static final String BIRTHDAY_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String PLAYER_ROLE_IDENTIFIER = "ROLE_PLAYER";
    public static final String ADMINISTRATOR_ROLE_IDENTIFIER = "ROLE_ADMINISTRATOR";

    public static final String USERNAME_REGEX = "^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public static final String PASSWORD_REGEX =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static final String SUPER_ADMIN_USERNAME = "admin123";
    public static final String SUPER_ADMIN_PASSWORD = "admin123A";
    public static final String SUPER_ADMIN_FIRST_NAME = "Adelin";
    public static final String SUPER_ADMIN_LAST_NAME = "Filimon";
    public static final String SUPER_ADMIN_EMAIL = "adelin_filimon24@yahoo.com";
}
