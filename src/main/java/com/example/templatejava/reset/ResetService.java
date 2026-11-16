package com.example.templatejava.reset;

import com.example.templatejava.accounts.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetService {
    private final AccountRepository accounts;
    public ResetService(AccountRepository accounts) { this.accounts = accounts; }
    public void reset() { accounts.clear(); }
}
