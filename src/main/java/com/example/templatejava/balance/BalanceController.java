package com.example.templatejava.balance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class BalanceController {
    private final BalanceService service;
    public BalanceController(BalanceService service) { this.service = service; }

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public long balance(@RequestParam("account_id") String accountId) {
        return service.balance(accountId);
    }
}
