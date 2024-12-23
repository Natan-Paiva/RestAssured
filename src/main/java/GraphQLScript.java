import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GraphQLScript {
    public static void main(String[] args){

        //Query
        int charID = 11021;
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId: Int!,$episodeId: Int!,$locationId: Int!, $characterName: String!, $episodeName: String!){\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n    episodes {\\n      id\\n    }\\n  }\\n  location(locationId: $locationId){\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId){\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: $characterName}){\\n    info{count}\\n    result{id type}\\n  }\\n  episodes(filters: {episode: $episodeName}){\\n    result{id name air_date}\\n  }\\n}\",\"variables\":{\"characterId\":"+charID+",\"episodeId\":11899,\"locationId\":16243,\"characterName\":\"Natan\",\"episodeName\":\"hulu\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String charName = js.getString("data.character.name");
        Assert.assertEquals(charName, "Natan");

        //Mutation
        String response2 = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId: Int!,$episodeId: Int!,$locationId: Int!, $characterName: String!, $episodeName: String!){\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n    episodes {\\n      id\\n    }\\n  }\\n  location(locationId: $locationId){\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId){\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: $characterName}){\\n    info{count}\\n    result{id type}\\n  }\\n  episodes(filters: {episode: $episodeName}){\\n    result{id name air_date}\\n  }\\n}\",\"variables\":{\"characterId\":11021,\"episodeId\":11899,\"locationId\":16243,\"characterName\":\"Natan\",\"episodeName\":\"hulu\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response2);
        JsonPath js1 = new JsonPath(response2);
        System.out.println(js1);
    }
}
