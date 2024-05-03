package wareHouse.Area.Controller;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;

@QuarkusTest
@TestHTTPEndpoint(AreaInfoController.class)
public class AreaInfoControllerTest {

    @Inject
    EntityManager em;

    @Test
    void testAddAreaInfoByPK() {
        JsonObject glquery = new JsonObject();
        glquery.put("branch", "A010");
        glquery.put("area", "V301");
        glquery.put("areaName", "萬歲大樓A棟入口");

        String redResult = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(glquery.encode())
                .when().post()
                .then().statusCode(201)
                .extract().body().asString();
        Assertions.assertEquals("{\"branch\":\"A010\",\"area\":\"V301\",\"areaName\":\"萬歲大樓A棟入口\"}", redResult);
    }

    @Test
    void testDeleteAreaInfoByPK() {
        String redString = given()
                .when().pathParam("branch", "A010")
                .pathParam("area", "V41U")
                .delete("/{branch}/{area}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertNotNull(redString);
        Assertions.assertTrue(redString.isEmpty());
    }

    @Test
    void testDeleteAreaInfoList() {

        JsonArray deleteRequest = new JsonArray();
        deleteRequest.add(new JsonObject().put("branch", "A011").put("area", "V41P"));

        String redResult = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(deleteRequest.encode())
                .when().delete("/bulk")
                .then().statusCode(200)
                .extract().body().asString();
        Assertions.assertNotNull(redResult);
        Assertions.assertTrue(redResult.isEmpty());
    }

    @Test
    void testUpdateAreaInfoByPK() {
        JsonObject glquery = new JsonObject();
        glquery.put("areaName", "萬歲大樓B棟入口左側地板");

        String redResult = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(glquery.encode())
                .when().pathParam("branch", "A010")
                .pathParam("area", "V40U")
                .put("/{branch}/{area}")
                .then().statusCode(200)
                .extract().body().asString();
        Assertions.assertNotNull(redResult);
        Assertions.assertFalse(redResult.isEmpty());

        AreaInfo updatedAreaInfo = em
                .createQuery("SELECT a FROM AreaInfo a WHERE a.branch = :branch AND a.area = :area", AreaInfo.class)
                .setParameter("branch", "A010")
                .setParameter("area", "V40U")
                .getSingleResult();

        Assertions.assertNotNull(updatedAreaInfo);
        Assertions.assertEquals("萬歲大樓B棟入口左側地板", updatedAreaInfo.getAreaName());
    }
}
