package com.michael.magaisa.demo_user_registration_app;

import com.michael.magaisa.demo_user_registration_app.appuser.AppUser;
import com.michael.magaisa.demo_user_registration_app.appuser.AppUserRole;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    @Test
    void createUser(){
        String endPoint = "http://localhost:8080/api/v1/registration";
        AppUserRole userRole = AppUserRole.USER;
        AppUser userToRegister = new AppUser(
                "Michael",
                "Magaisa",
                "m@mail.com",
                "password",
                userRole
        );
        var response = given().body(userToRegister).when().post(endPoint).then();
        response.log().body();
    }
}
