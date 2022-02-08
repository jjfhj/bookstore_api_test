package com.github.jjfhj.tests;

import com.github.jjfhj.lombok.UserRequestData;
import com.github.jjfhj.lombok.UserResponseData;

import static com.github.jjfhj.config.Credentials.CREDENTIALS_CONFIG;

public class TestData {

    public static final UserResponseData USER_RESPONSE_DATA = new UserResponseData();
    public static final UserRequestData USER_REQUEST_DATA = new UserRequestData();

    public static final String USER_NAME = CREDENTIALS_CONFIG.userName();
    public static final String PASSWORD = CREDENTIALS_CONFIG.password();

    public static UserRequestData setUserLoginData() {
        USER_REQUEST_DATA.setUserName(USER_NAME);
        USER_REQUEST_DATA.setPassword(PASSWORD);
        return USER_REQUEST_DATA;
    }

    public static String addingData = "{\"userId\": \"" + USER_RESPONSE_DATA.getUserId() + "\"," +
            "\"collectionOfIsbns\" : [{\"isbn\":\"9781449325862\"}]}";

    public static String removingData = "{\"isbn\":\"9781449325862\"," +
            "\"userId\": \"" + USER_RESPONSE_DATA.getUserId() + "\"}";
}
