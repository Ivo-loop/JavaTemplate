package com.example.templatejava.event;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferApiTests extends ApiTestSupport {

    @Test
    void createsDestinationDuringTransfer() throws Exception {
        // Arrange
        createAccount("100", 15);
        String request = "{\"type\":\"transfer\",\"origin\":\"100\",\"destination\":\"300\",\"amount\":15}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(201, response.statusCode());
        assertEquals("{\"origin\":{\"id\":\"100\",\"balance\":0},\"destination\":{\"id\":\"300\",\"balance\":15}}", response.body());
    }

    @Test
    void transfersBetweenExistingAccounts() throws Exception {
        // Arrange
        createAccount("100", 100);
        createAccount("300", 300);
        String request = "{\"type\":\"transfer\",\"origin\":\"100\",\"destination\":\"300\",\"amount\":15}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(201, response.statusCode());
        assertEquals("{\"origin\":{\"id\":\"100\",\"balance\":85},\"destination\":{\"id\":\"300\",\"balance\":315}}", response.body());
    }

    @Test
    void returnsNotFoundForMissingOrigin() throws Exception {
        // Arrange
        createAccount("300", 15);
        String request = "{\"type\":\"transfer\",\"origin\":\"200\",\"destination\":\"300\",\"amount\":15}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(404, response.statusCode());
        assertEquals("{\"details\":\"Account not found\"}", response.body());
        assertEquals("15", get("/balance?account_id=300").body());
    }

    @Test
    void rejectsInsufficientFunds() throws Exception {
        // Arrange
        createAccount("100", 100);
        createAccount("300", 300);
        String request = "{\"type\":\"transfer\",\"origin\":\"100\",\"destination\":\"300\",\"amount\":1000}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"details\":\"Insufficient funds\"}", response.body());
        assertEquals("100", get("/balance?account_id=100").body());
        assertEquals("300", get("/balance?account_id=300").body());
    }

    @Test
    void rejectsTransferToSameAccount() throws Exception {
        // Arrange
        createAccount("100", 100);
        String request = "{\"type\":\"transfer\",\"origin\":\"100\",\"destination\":\"100\",\"amount\":1}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(404, response.statusCode());
        assertEquals("0", response.body());
        assertEquals("100", get("/balance?account_id=100").body());
    }

    @Test
    void rejectsMissingDestination() throws Exception {
        // Arrange
        String request = "{\"type\":\"transfer\",\"origin\":\"100\",\"amount\":1}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }
}
