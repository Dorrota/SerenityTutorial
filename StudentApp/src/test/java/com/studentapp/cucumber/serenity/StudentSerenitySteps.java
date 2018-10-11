package com.studentapp.cucumber.serenity;

import com.studentapp.model.StudentClass;
import com.studentapp.utils.ReusableSpecifications;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.List;

public class StudentSerenitySteps {

    @Step("Creating student with firstName:{0}, lastName: {1}, email: {2}, programme: {3}, courses: {4}")
    public ValidatableResponse createStudent(String firstName, String lastName, String email, String programme, List<String> courses) {

        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        return SerenityRest.rest().given()
                .spec(ReusableSpecifications.getRequestSpecification())
                .when().body(student)
                .post().then();
    }

    @Step("Getting the student info by firstName: {0}")
    public HashMap<String, Object> getStudentInfoByFirstName(String firstName) {

        return SerenityRest.rest().log().all()
                .given().when().get("/list").then().statusCode(200)
                .extract().path("findAll{it.firstName=='" + firstName + "'}.get(0)");
    }

    @Step("Updating student info with studentID: {0}, firstName:{1}, lastName: {2}, email: {3}, programme: {4}, courses: {5}")
    public ValidatableResponse updateStudent(int studentId, String firstName, String lastName, String email, String programme, List<String> courses) {

        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        return SerenityRest.rest().given()
                .spec(ReusableSpecifications.getRequestSpecification()).log().all()
                .when().body(student)
                .put("/" + studentId).then();
    }

    @Step("Deleting student with ID: {0}")
    public void deleteStudent(int studentId) {
        SerenityRest.rest().given().when().delete("/" + studentId);
    }

    @Step("Getting info of the student with ID: {0}")
    public ValidatableResponse getStudentById(int studentId) {
        return
                SerenityRest.rest().given().when().get("/" + studentId).then();
    }
}
