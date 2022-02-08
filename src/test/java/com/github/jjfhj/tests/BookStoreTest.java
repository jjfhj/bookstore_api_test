package com.github.jjfhj.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.jjfhj.JiraIssue;
import com.github.jjfhj.JiraIssues;
import com.github.jjfhj.Layer;
import com.github.jjfhj.Microservice;
import com.github.jjfhj.config.Credentials;
import com.github.jjfhj.lombok.BookListData;
import com.github.jjfhj.models.UserToken;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.github.jjfhj.specs.Specs.request;
import static com.github.jjfhj.specs.Specs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Layer("rest")
@Owner("kgordienko")
@Tags({@Tag("Web"), @Tag("API")})
@JiraIssues({@JiraIssue("HOMEWORK-325")})
@Link(name = "Book Store", url = "https://demoqa.com/books")
@DisplayName("Тестирование веб-приложения Book Store")
public class BookStoreTest {

    public static final String USER_NAME = Credentials.CREDENTIALS_CONFIG.userName();
    public static final String PASSWORD = Credentials.CREDENTIALS_CONFIG.password();

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        String data = "{" +
                "  \"userName\": \"" + USER_NAME + "\"," +
                "  \"password\": \"" + PASSWORD + "\"}";

        step("Получение токена авторизации и userId", () -> {
            given()
                    .spec(request)
                    .body(data)
                    .when()
                    .post("/Account/v1/Authorized")
                    .then()
                    .spec(responseSpec)
                    .body(is("true"));
        });
    }

    @Test
    @DisplayName("Успешная генерация токена (с использованием модели)")
    @Tags({@Tag("Critical"), @Tag("Highest")})
    @Microservice("Account")
    @Feature("Генерация токена")
    @Story("Метод POST /Account/v1/GenerateToken")
    @Severity(SeverityLevel.CRITICAL)
    void tokenGenerationWithModelTest() {

        String userData = "{" +
                "  \"userName\": \"" + USER_NAME + "\"," +
                "  \"password\": \"" + PASSWORD + "\"}";

        UserToken data = given()
                .spec(request)
                .body(userData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .extract().as(UserToken.class);

        assertThat(data.getToken()).isNotNull();
        assertThat(data.getStatus()).isEqualTo("Success");
        assertThat(data.getResult()).isEqualTo("User authorized successfully.");
    }

    @Test
    @DisplayName("Отображение списка всех книг (с использованием Lombok)")
    @Tags({@Tag("Major"), @Tag("Medium")})
    @Microservice("BookStore")
    @Feature("Список книг")
    @Story("Метод GET /BookStore/v1/Books")
    @Severity(SeverityLevel.NORMAL)
    void displayAListOfAllBooksWithLombokModelTest() {
        BookListData data = given()
                .spec(request)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .extract().as(BookListData.class);

        assertEquals("9781449325862", data.getBooks()[0].getIsbn());
        assertEquals("Git Pocket Guide", data.getBooks()[0].getTitle());
    }

    @Test
    @DisplayName("Отображение списка всех книг (с использованием Groovy)")
    @Tags({@Tag("Major"), @Tag("Medium")})
    @Microservice("BookStore")
    @Feature("Список книг")
    @Story("Метод GET /BookStore/v1/Books")
    @Severity(SeverityLevel.NORMAL)
    void displayAListOfAllBooksWithGroovyTest() {
        given()
                .spec(request)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .body("books", notNullValue(),
                        "books.findAll{it.website =~/http.*?/}.website.flatten()",
                        hasItem("http://chimera.labs.oreilly.com/books/1230000000561/index.html"));
    }

    @Test
    @DisplayName("Отображение определенной книги по ISBN в списке всех книг")
    @Tags({@Tag("Major"), @Tag("Medium")})
    @Microservice("BookStore")
    @Feature("Список книг")
    @Story("Метод GET /BookStore/v1/Book")
    @Severity(SeverityLevel.NORMAL)
    void displayABookByISBNInTheListOfAllBooksTest() {
        given()
                .spec(request)
                .queryParam("ISBN", "9781449325862")
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .body(notNullValue(),
                        matchesJsonSchemaInClasspath("schema/AddItemToCartTestSchema.json"))
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
                .spec(request)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImpqZmhqIiwicGFzc3dvcmQiOiJ0NDRAaTlCayIsImlhdCI6MTY0NDI1MTI0MX0.afR0yMntz6RN2SElun6vbCeLeSIcFAV6mQ0_aA_QLq8")
                .body(data)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().headers().and().log().body()
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
                .spec(request)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImpqZmhqIiwicGFzc3dvcmQiOiJ0NDRAaTlCayIsImlhdCI6MTY0NDI1MTI0MX0.afR0yMntz6RN2SElun6vbCeLeSIcFAV6mQ0_aA_QLq8")
                .body(data)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().headers().and().log().body()
                .statusCode(204)
                .body(is(""));
    }
}
