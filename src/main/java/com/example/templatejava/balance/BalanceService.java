package com.example.templatejava.balance;

import com.example.templatejava.accounts.AccountRepository;
import com.example.templatejava.accounts.BankingException;
import static com.example.templatejava.accounts.BankingException.Reason.ACCOUNT_NOT_FOUND;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    private final AccountRepository accounts;
    public BalanceService(AccountRepository accounts) { this.accounts = accounts; }

    public long balance(String accountId) {
        return accounts.findById(accountId)
                .orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND)).balance();
    }
}
