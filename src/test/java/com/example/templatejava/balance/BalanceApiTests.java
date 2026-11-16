package com.example.templatejava.balance;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceApiTests extends ApiTestSupport {

    @Test
    void returnsBalanceForExistingAccount() throws Exception {
        // Arrange
        createAccount("100", 20);
        String path = "/balance?account_id=100";

        // Act
        var response = get(path);

        // Assert
        assertEquals(200, response.statusCode());
        assertEquals("20", response.body());
    }

    @Test
    void returnsNotFoundForMissingAccount() throws Exception {
        // Arrange
        String path = "/balance?account_id=999";

        // Act
        var response = get(path);

        // Assert
        assertEquals(404, response.statusCode());
        assertEquals("{\"details\":\"Account not found\"}", response.body());
    }
}
