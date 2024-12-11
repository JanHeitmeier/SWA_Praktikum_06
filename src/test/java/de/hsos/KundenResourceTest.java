package de.hsos;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class KundenResourceTest {

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setUp() {
        // Clear any existing customers by calling DELETE on each
        given()
                .when()
                .get("/kunden")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("", Kunde.class)
                .forEach(kunde ->
                        given()
                                .when()
                                .delete("/kunden/" + kunde.getId())
                                .then()
                                .statusCode(204)
                );
    }

    @Test
    @DisplayName("Test CRUD operations for single customer")
    void testSingleCustomerOperations() {
        // Create customer
        Kunde kunde = new Kunde("Test Kunde");
        long kundenId = given()
                .contentType(ContentType.JSON)
                .body(kunde)
                .when()
                .post("/kunden")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("id");


        // Get customer
        given()
                .when()
                .get("/kunden/" + kundenId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Kunde"));

        // Add address
        Adresse adresse = new Adresse("12345", "Test Stadt", "Test Str.", "1");
        given()
                .contentType(ContentType.JSON)
                .body(adresse)
                .when()
                .post("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(201);

        // Verify address
        given()
                .when()
                .get("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(200)
                .body("plz", equalTo("12345"));

        // Delete customer
        given()
                .when()
                .delete("/kunden/" + kundenId)
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Test concurrent customer creation with ApplicationScoped/ApplicationScoped")
    void testConcurrentCustomerCreation() throws InterruptedException {
        int numberOfThreads = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    Kunde kunde = new Kunde("Concurrent Kunde " + index);
                    given()
                            .contentType(ContentType.JSON)
                            .body(kunde)
                            .when()
                            .post("/kunden")
                            .then()
                            .statusCode(201);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);

        // Verify number of created customers
        given()
                .when()
                .get("/kunden")
                .then()
                .statusCode(200)
                .body("size()", equalTo(numberOfThreads));
    }

    @Test
    @DisplayName("Test concurrent address updates with RequestScoped/ApplicationScoped")
    void testConcurrentAddressUpdates() throws InterruptedException {
        // Create initial customer
        Kunde kunde = new Kunde("Address Test Kunde");
        long kundenId = given()
                .contentType(ContentType.JSON)
                .body(kunde)
                .when()
                .post("/kunden")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("id");


        int numberOfThreads = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    Adresse adresse = new Adresse("1234" + index, "Stadt " + index, "Str " + index, String.valueOf(index));
                    given()
                            .contentType(ContentType.JSON)
                            .body(adresse)
                            .when()
                            .put("/kunden/" + kundenId + "/adresse")
                            .then()
                            .statusCode(200);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);

        // Verify final address exists
        given()
                .when()
                .get("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Test scope behavior with missing customer")
    void testScopeBehaviorWithMissingCustomer() {
        given()
                .when()
                .get("/kunden/99999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test address operations with different scopes")
    void testAddressOperations() {
        // Create customer
        Kunde kunde = new Kunde("Address Test");
        long kundenId = given()
                .contentType(ContentType.JSON)
                .body(kunde)
                .when()
                .post("/kunden")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath().getLong("id");


        // Add address
        Adresse adresse = new Adresse("54321", "New Stadt", "New Str.", "2");
        given()
                .contentType(ContentType.JSON)
                .body(adresse)
                .when()
                .post("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(201);

        // Update address
        adresse.setHausnr("3");
        given()
                .contentType(ContentType.JSON)
                .body(adresse)
                .when()
                .put("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(200);

        // Delete address
        given()
                .when()
                .delete("/kunden/" + kundenId + "/adresse")
                .then()
                .statusCode(204);
    }
}