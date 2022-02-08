package com.github.jjfhj.tests;

import static com.github.jjfhj.tests.BookStoreTest.USER_RESPONSE_DATA;

public class TestData {

    public static String isbn = "9781449325862";
    public static String title = "Git Pocket Guide";

    public static String addingData = "{\"userId\": \"" + USER_RESPONSE_DATA.getUserId() + "\"," +
            "\"collectionOfIsbns\" : [{\"isbn\":\"" + isbn + "\"}]}";

    public static String removingData = "{\"isbn\":\"" + isbn + "\"," +
            "\"userId\": \"" + USER_RESPONSE_DATA.getUserId() + "\"}";
}
