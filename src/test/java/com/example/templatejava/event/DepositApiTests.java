package com.example.templatejava.event;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DepositApiTests extends ApiTestSupport {

    @Test
    void createsAccountWithInitialBalance() throws Exception {
        // Arrange
        String request = "{\"type\":\"deposit\",\"destination\":\"100\",\"amount\":10}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(201, response.statusCode());
        assertEquals("{\"destination\":{\"id\":\"100\",\"balance\":10}}", response.body());
    }

    @Test
    void depositsIntoExistingAccount() throws Exception {
        // Arrange
        createAccount("100", 10);
        String request = "{\"type\":\"deposit\",\"destination\":\"100\",\"amount\":10}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(201, response.statusCode());
        assertEquals("{\"destination\":{\"id\":\"100\",\"balance\":20}}", response.body());
    }

    @Test
    void rejectsZeroAmount() throws Exception {
        // Arrange
        String request = "{\"type\":\"deposit\",\"destination\":\"100\",\"amount\":0}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }

    @Test
    void rejectsMissingDestination() throws Exception {
        // Arrange
        String request = "{\"type\":\"deposit\",\"amount\":10}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }
}
