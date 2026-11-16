package com.example.templatejava.event;

import com.example.templatejava.accounts.ApiTestSupport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventApiTests extends ApiTestSupport {

    @Test
    void rejectsMalformedJsonWithJsonError() throws Exception {
        // Arrange
        String request = "{";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
    }

    @Test
    void rejectsMissingFields() throws Exception {
        // Arrange
        String request = "{}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }

    @Test
    void rejectsUnknownEventType() throws Exception {
        // Arrange
        String request = "{\"type\":\"unknown\",\"origin\":\"100\",\"amount\":1}";

        // Act
        var response = postEvent(request);

        // Assert
        assertEquals(400, response.statusCode());
        assertEquals("{\"error\":\"Invalid event\"}", response.body());
    }
}
