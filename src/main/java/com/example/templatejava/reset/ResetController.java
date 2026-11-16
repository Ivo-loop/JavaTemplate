package com.example.templatejava.reset;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {
    private final ResetService service;
    public ResetController(ResetService service) { this.service = service; }

    @PostMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> reset() { service.reset(); return Map.of("message", "OK"); }
}
