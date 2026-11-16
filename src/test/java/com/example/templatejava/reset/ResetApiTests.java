package com.example.templatejava.reset;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResetApiTests extends ApiTestSupport {

    @Test
    void clearsAccounts() throws Exception {
        // Arrange
        createAccount("100", 10);

        // Act
        var response = post("/reset", "");

        // Assert
        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"OK\"}", response.body());
        assertEquals(404, get("/balance?account_id=100").statusCode());
    }
}
