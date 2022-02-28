package com.github.jjfhj.data;

import com.github.jjfhj.lombok.*;

import static com.github.jjfhj.config.App.CREDENTIALS_CONFIG;

public class TestData {

    public static final UserResponseData USER_RESPONSE_DATA = new UserResponseData();
    public static final UserRequestData USER_REQUEST_DATA = new UserRequestData();
    public static final CollectionOfIsbns COLLECTION_OF_ISBNS = new CollectionOfIsbns();
    public static final BookDataForAdding BOOK_DATA_FOR_ADDING = new BookDataForAdding();
    public static final BookDataForRemoving BOOK_DATA_FOR_REMOVING = new BookDataForRemoving();

    public static final String USER_NAME = CREDENTIALS_CONFIG.userName();
    public static final String PASSWORD = CREDENTIALS_CONFIG.password();

    public static String isbn = "9781449325862";
    public static String title = "Git Pocket Guide";

    public static UserRequestData setUserLoginData() {
        USER_REQUEST_DATA.setUserName(USER_NAME);
        USER_REQUEST_DATA.setPassword(PASSWORD);
        return USER_REQUEST_DATA;
    }

    public static CollectionOfIsbns setIsbn() {
        COLLECTION_OF_ISBNS.setIsbn(isbn);
        return COLLECTION_OF_ISBNS;
    }

    public static BookDataForAdding setBookDataForAdding() {
        BOOK_DATA_FOR_ADDING.setCollectionOfIsbns(new CollectionOfIsbns[]{setIsbn()});
        BOOK_DATA_FOR_ADDING.setUserId(USER_RESPONSE_DATA.getUserId());
        return BOOK_DATA_FOR_ADDING;
    }

    public static BookDataForRemoving setBookDataForRemoving() {
        BOOK_DATA_FOR_REMOVING.setIsbn(isbn);
        BOOK_DATA_FOR_REMOVING.setUserId(USER_RESPONSE_DATA.getUserId());
        return BOOK_DATA_FOR_REMOVING;
    }
}
