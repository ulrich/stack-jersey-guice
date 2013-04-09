package fr.mappy;

import com.jayway.restassured.RestAssured;
import fr.mappy.fixture.ServerRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class ServerTest {
    @Rule
    public ServerRule serverRule = new ServerRule(7999);

    @Before
    public void before() {
        RestAssured.baseURI = "http://127.0.0.1:7999/services/api/v1";
    }

    @Test
    public void search_users_with_valid_name_return_filled_collection() {
        given().
            param("name", "ulrich").

        expect().
            statusCode(200).

        when().
            get("/users");
    }
}
