package com.studentapp.junit;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Manual;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI="http://localhost:8080/student";
    }

    @Test
    public void getAllStudents() {
        SerenityRest.given().when().get("/list").then().log().all().statusCode(200);
    }

    @Test
    public void failigTest() {
        SerenityRest.given().when().get("/list").then().statusCode(500);
    }

    @Pending
    @Test
    public void pendingTest() {

    }

    @Ignore
    @Test
    public void skippedTest() {

    }

    @Test
    public void testWithError() {
        System.out.println("This is an error" + 5/0);
    }

    @Test
    public void fileNotExists() throws FileNotFoundException {
        File file = new File("c:\\Users\\doko\\Desktop\\text.txt");
        FileReader fr = new FileReader(file);
    }

    @Manual
    @Test
    public void manualTest() {

    }

    @Title("This is a test title")
    @Test
    public void testWithTitle() {
        SerenityRest.given().when().get("/list").then().statusCode(200);
    }
}







