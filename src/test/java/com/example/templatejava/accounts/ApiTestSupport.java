package com.example.templatejava.accounts;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ApiTestSupport {
    @LocalServerPort private int port;
    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void resetState() throws Exception {
        assertEquals(200, post("/reset", "").statusCode());
    }

    protected HttpResponse<String> get(String path) throws Exception {
        return json(client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .GET().build(), HttpResponse.BodyHandlers.ofString()));
    }
    protected HttpResponse<String> postEvent(String body) throws Exception { return post("/event", body); }
    protected HttpResponse<String> post(String path, String body) throws Exception {
        return json(client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString()));
    }
    private HttpResponse<String> json(HttpResponse<String> response) {
        assertTrue(response.headers().firstValue("Content-Type").orElse("").startsWith("application/json"));
        return response;
    }
    protected void createAccount(String id, long amount) throws Exception {
        String body = "{\"type\":\"deposit\",\"destination\":\"" + id + "\",\"amount\":" + amount + "}";
        assertEquals(201, postEvent(body).statusCode());
    }
}
