package com.studentapp.junit;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReusableSpecifications;
import com.studentapp.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTests extends TestBase {

    static String firstName = "SMOKEUSER" + TestUtils.getRandomValue();
    static String lastName = "SMOKEUSER" + TestUtils.getRandomValue();
    static String programme = "ComputerScience";
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static int studentId;

    @Steps
    StudentSerenitySteps steps;


    @Title("Creating student test")
    @Test
    public void test01CreateStudent() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("JAVA");
        courses.add("C++");

/*        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);*/
        steps.createStudent(firstName, lastName, email, programme, courses).statusCode(201)
                .spec(ReusableSpecifications.getResponseSpecification());

    }

    @Title("Veryfing studet")
    @Test
    public void test02GetStudent() {
        // String p1 = "findAll";
         /*HashMap<String, Object> value = SerenityRest.rest().log().all()
                 .given().when().get("/list").then().statusCode(200)
                .extract().path("findAll{it.firstName=='" + firstName + "'}.get(0)");*/

         HashMap<String, Object> value = steps.getStudentInfoByFirstName(firstName);
         System.out.println("Test 2 student " + value);

         assertThat(value,hasValue(firstName));
         studentId = (int) value.get("id");
    }

    @Title("Update and verify student")
    @Test
    public void test03UpdateStudent() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("JAVA");
        courses.add("C++");

        firstName = firstName + "_Update";

/*        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        SerenityRest.rest().given().contentType(ContentType.JSON).log().all()
                .when().body(student).put("/" + studentId);*/

        steps.updateStudent(studentId, firstName, lastName, email, programme, courses);

        HashMap<String, Object> value = steps.getStudentInfoByFirstName(firstName);

        assertThat(value,hasValue(firstName));
    }

    @Title("Delete student and varyfing deletion")
    @Test
    public void test04DeleteStudent() {

        steps.deleteStudent(studentId);

        steps.getStudentById(studentId).statusCode(404);
    }
}
