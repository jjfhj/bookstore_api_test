package com.github.jjfhj.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.jjfhj.lombok.UserResponseData;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

import static com.github.jjfhj.specs.Specs.request;
import static com.github.jjfhj.specs.Specs.responseSpec;
import static com.github.jjfhj.tests.BookStoreTest.USER_RESPONSE_DATA;
import static com.github.jjfhj.tests.BookStoreTest.setUserLoginData;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class TestBase {

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        step("Получение токена авторизации и userId (с использованием Lombok)", () -> {
            UserResponseData userResponseData = given()
                    .spec(request)
                    .body(setUserLoginData())
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .spec(responseSpec)
                    .extract().as(UserResponseData.class);

            USER_RESPONSE_DATA.setUserId(userResponseData.getUserId());
            USER_RESPONSE_DATA.setToken(userResponseData.getToken());
        });
    }
}
