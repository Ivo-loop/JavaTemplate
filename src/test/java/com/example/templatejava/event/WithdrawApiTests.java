package com.example.templatejava.event;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WithdrawApiTests extends ApiTestSupport {

    @Test
    void withdrawsFromExistingAccount() throws Exception {
        // Arrange
        createAccount("100", 20);
        String request = "{\"type\":\"withdraw\",\"origin\":\"100\",\"amount\":5}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(201, response.statusCode());
        assertEquals("{\"origin\":{\"id\":\"100\",\"balance\":15}}", response.body());
    }

    @Test
    void returnsNotFoundForMissingAccount() throws Exception {
        // Arrange
        String request = "{\"type\":\"withdraw\",\"origin\":\"200\",\"amount\":10}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(404, response.statusCode());
        assertEquals("{\"details\":\"Account not found\"}", response.body());
    }

    @Test
    void rejectsInsufficientFunds() throws Exception {
        // Arrange
        createAccount("100", 20);
        String request = "{\"type\":\"withdraw\",\"origin\":\"100\",\"amount\":1000}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"details\":\"Insufficient funds\"}", response.body());
        assertEquals("20", get("/balance?account_id=100").body());
    }

    @Test
    void rejectsNegativeAmount() throws Exception {
        // Arrange
        String request = "{\"type\":\"withdraw\",\"origin\":\"100\",\"amount\":-1}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }
}
