package com.github.jjfhj.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.github.jjfhj.filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.HEADERS;

public class Specs {
    public static RequestSpecification request = with()
            .baseUri("https://demoqa.com")
//            .basePath("/BookStore/v1")
            .log().all()
            .contentType(ContentType.JSON)
            .filter(customLogFilter().withCustomTemplates());

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(HEADERS)
            .log(BODY)
            .expectStatusCode(200)
//            .expectBody(containsString("success"))
            .build();
}
