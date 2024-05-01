package wareHouse.Vehicle.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@QuarkusTest
@TestHTTPEndpoint(VehicleMasterController.class)
public class VehicleMasterControllerTest {
    @Test
    void testAddVehicleMasterByPK() {
        JsonObject glquery = new JsonObject();
        glquery.put("vehicleId", "SF000004");
        glquery.put("vehicleTypeId", "SF");
        glquery.put("branch", "A011");
        glquery.put("storeLocation", "LW1002");
        glquery.put("pauseCode", "00");

        String redResult = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(glquery.toString())
                .when().post("/vehicleMaster")
                .then().statusCode(201)
                .extract().body().asString();
        Assertions.assertNotNull(redResult);

        JsonObject result = new JsonObject(redResult);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertTrue(result.size() > 0));
    }

    @Test
    void testAddVehicleMasterDetailByPK() {
        JsonObject vehicleMaster = new JsonObject();
        vehicleMaster.put("vehicleId", "PG000004");
        vehicleMaster.put("vehicleTypeId", "PG");
        vehicleMaster.put("branch", "A011");
        vehicleMaster.put("storeLocation", "LW1002");
        vehicleMaster.put("pauseCode", "00");

        JsonObject Detail = new JsonObject();
        Detail.put("vehicleId", "PG000004");
        Detail.put("itemId", 548);
        Detail.put("lotNumber", "20230831");
        Detail.put("storeQuantity", 30);
        Detail.put("storeUom", "KG");
        JsonArray vehicleDetail = new JsonArray();
        vehicleDetail.add(Detail);

        JsonObject glquery = new JsonObject();
        glquery.put("vehicleMaster", vehicleMaster);
        glquery.put("vehicleDetail", vehicleDetail);

        String result = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(glquery.toString())
                .post()
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .body()
                .asString();
        Assertions.assertNotNull(result);

        JsonObject resultA = new JsonObject(result);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(resultA),
                () -> Assertions.assertTrue(resultA.size() > 0));
    }

    @Test
    void testDeleteVehicleMasterByPK() {
        String redString = given()
                .when().pathParam("vehicleId", "SF000001")
                .delete("/{vehicleId}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertNotNull(redString);
    }

    @Test
    void testUpdateVehicleMasterByPK() {
        JsonObject glquery = new JsonObject();
        glquery.put("branch", "A010");
        glquery.put("storeLocation", "LW1001");

        String redResult = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(glquery.toString())
                .when().pathParam("vehicleId", "PG000004")
                .put("/{vehicleId}")
                .then().statusCode(200)
                .extract().body().asString();
        Assertions.assertNotNull(redResult);
    }
}
