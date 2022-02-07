package com.github.jjfhj.tests;

import com.github.jjfhj.JiraIssue;
import com.github.jjfhj.JiraIssues;
import com.github.jjfhj.Layer;
import com.github.jjfhj.Microservice;
import com.github.jjfhj.config.CredentialsConfig;
import io.qameta.allure.*;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Layer("rest")
@Owner("kgordienko")
@Tags({@Tag("Web"), @Tag("API")})
@JiraIssues({@JiraIssue("HOMEWORK-325")})
@Link(name = "Book Store", url = "https://demoqa.com/books")
@DisplayName("Тестирование веб-приложения Book Store")
public class BookStoreTest {

    private static final CredentialsConfig CREDENTIALS_CONFIG = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
    private static final String USER_NAME = CREDENTIALS_CONFIG.userName();
    private static final String PASSWORD = CREDENTIALS_CONFIG.password();

    @Test
    @DisplayName("Успешная авторизация через UserName и Password")
    @Tags({@Tag("Critical"), @Tag("Highest")})
    @Microservice("Account")
    @Feature("Авторизация")
    @Story("Метод POST /Account/v1/Authorized")
    @Severity(SeverityLevel.CRITICAL)
    void authorizationViaUserNameAndPasswordTest() {

        String data = "{" +
                "  \"userName\": \"" + USER_NAME + "\"," +
                "  \"password\": \"" + PASSWORD + "\"}";

        given()
                .contentType(JSON)
                .baseUri("https://demoqa.com")
                .body(data)
                .when()
                .post("/Account/v1/Authorized")
                .then().log().all()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    @DisplayName("Успешная генерация токена")
    @Tags({@Tag("Critical"), @Tag("Highest")})
    @Microservice("Account")
    @Feature("Генерация токена")
    @Story("Метод POST /Account/v1/GenerateToken")
    @Severity(SeverityLevel.CRITICAL)
    void tokenGenerationTest() {

        String data = "{" +
                "  \"userName\": \"" + USER_NAME + "\"," +
                "  \"password\": \"" + PASSWORD + "\"}";

        given()
                .contentType(JSON)
                .baseUri("https://demoqa.com")
                .body(data)
                .when()
                .post("/Account/v1/GenerateToken")
                .then().log().all()
                .statusCode(200)
                .body("token", notNullValue(),
                        "status", is("Success"),
                        "result", is("User authorized successfully."));
    }

    @Test
    @DisplayName("Отображение списка всех книг")
    @Tags({@Tag("Major"), @Tag("Medium")})
    @Microservice("BookStore")
    @Feature("Список книг")
    @Story("Метод GET /BookStore/v1/Books")
    @Severity(SeverityLevel.NORMAL)
    void displayAListOfAllBooksTest() {
        given()
                .contentType(JSON)
                .baseUri("https://demoqa.com")
                .when()
                .get("/BookStore/v1/Books")
                .then().log().all()
                .statusCode(200)
                .body("books", notNullValue(),
                        "books[0].isbn", is("9781449325862"),
                        "books[0].title", is("Git Pocket Guide"));
    }

    @Test
    @DisplayName("Отображение определенной книги по ISBN в списке всех книг")
    @Tags({@Tag("Major"), @Tag("Medium")})
    @Microservice("BookStore")
    @Feature("Список книг")
    @Story("Метод GET /BookStore/v1/Book?ISBN=")
    @Severity(SeverityLevel.NORMAL)
    void displayABookByISBNInTheListOfAllBooksTest() {
        given()
                .contentType(JSON)
                .baseUri("https://demoqa.com")
                .formParam("9781449325862")
                .when()
                .get("/BookStore/v1/Book?ISBN=9781449325862")
                .then().log().all()
                .statusCode(200)
                .body(notNullValue())
                .body("isbn", is("9781449325862"),
                        "title", is("Git Pocket Guide"));
    }

    @Test
    @DisplayName("Добавление книги в профиль пользователя")
    @Tags({@Tag("Blocker"), @Tag("High")})
    @Microservice("BookStore")
    @Feature("Список добавленных книг в профиле пользователя")
    @Story("Метод POST /BookStore/v1/Books")
    @Severity(SeverityLevel.BLOCKER)
    void addingABookToAUserProfileTest() {

        String data = "{\"userId\":\"43b6a188-3255-4be0-86f1-1cf56de4f17b\"," +
                "\"collectionOfIsbns\" : [{\"isbn\":\"9781449325862\"}]}";

        given()
                .contentType(JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImpqZmhqIiwicGFzc3dvcmQiOiJ0NDRAaTlCayIsImlhdCI6MTY0NDI1MTI0MX0.afR0yMntz6RN2SElun6vbCeLeSIcFAV6mQ0_aA_QLq8")
                .baseUri("https://demoqa.com")
                .body(data)
                .when()
                .post("/BookStore/v1/Books")
                .then().log().all()
                .statusCode(201)
                .body("books[0].isbn", is("9781449325862"));
    }

    @Test
    @DisplayName("Удаление добавленной книги из профиля пользователя")
    @Tags({@Tag("Blocker"), @Tag("High")})
    @Microservice("BookStore")
    @Feature("Список добавленных книг в профиле пользователя")
    @Story("Метод DELETE /BookStore/v1/Book")
    @Severity(SeverityLevel.BLOCKER)
    void removingAnAddedBookFromAUserProfileTest() {

        String data = "{\"isbn\":\"9781449325862\"," +
                "\"userId\":\"43b6a188-3255-4be0-86f1-1cf56de4f17b\"}";

        given()
                .contentType(JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImpqZmhqIiwicGFzc3dvcmQiOiJ0NDRAaTlCayIsImlhdCI6MTY0NDI1MTI0MX0.afR0yMntz6RN2SElun6vbCeLeSIcFAV6mQ0_aA_QLq8")
                .baseUri("https://demoqa.com")
                .body(data)
                .when()
                .delete("/BookStore/v1/Book")
                .then().log().all()
                .statusCode(204)
                .body(is(""));
    }
}
