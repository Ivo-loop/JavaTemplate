package com.example.templatejava.accounts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public synchronized Optional<Account> findById(String id) { return Optional.ofNullable(accounts.get(id)); }
    public synchronized void save(Account account) { accounts.put(account.id(), account); }
    public synchronized void clear() { accounts.clear(); }
}
