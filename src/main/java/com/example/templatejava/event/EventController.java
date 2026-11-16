package com.example.templatejava.event;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class EventController {
    private final EventService service;
    public EventController(EventService service) { this.service = service; }

    @PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> event(@RequestBody EventService.Event request) {
        EventService.Result result = service.execute(request);
        Object body = result.origin() == null ? Map.of("destination", result.destination())
                : result.destination() == null ? Map.of("origin", result.origin())
                : new TransferResponse(result.origin(), result.destination());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    private record TransferResponse(com.example.templatejava.accounts.Account origin,
                                    com.example.templatejava.accounts.Account destination) {}
}
