import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;


public class BugTest{
    public static void main(String[] args){
        RestAssured.baseURI="https://natanabpaiva.atlassian.net";

        String response = given().header("Content-Type", "application/json").header("Authorization", "Here goes the base64 authorization")
                .body("{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"key\": \"RES\"\n" +
                        "    },\n" +
                        "    \"summary\": \"Dnd character\",\n" +
                        "    \"description\": \"Hjolmir.\",\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}").log().all()
                .post("rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(response);
        String id = js.getString("id");
        System.out.println(id);

        given().pathParam("key", id)
                .header("X-Atlassian-Token", "no-check")
                .header("Authorization", "Basic bmF0YW5hYnBhaXZhQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjB6bDdKdVp5dG5OUGRFWnU5amZHV1pib2RaM3pMODZpcmIyQzQ0OXRWTk01bWFpeXoyQjJKYmx1WFU5RzVhRFhFZWxvOERlMjhNOWJQQWtGVTRhMkwtTlFmeXEySlBCR2lsSUExWlJFUW1sNlV0V2xEbkNGdFJweFdnd01TbGJJWU45c0dDdjRvTENhQzllYTYyeEZiV1ZTRl93WTBhSlcwckNycm1tNUNyUzA9NDk0MDM0M0E=")
                .multiPart("file", new File("/Users/Natan/Downloads/hjolmir.png"))
                .post("rest/api/2/issue/{key}/attachments")
                .then().assertThat().statusCode(200);
    }
}
