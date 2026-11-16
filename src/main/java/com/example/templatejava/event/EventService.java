package com.example.templatejava.event;

import com.example.templatejava.accounts.Account;
import com.example.templatejava.accounts.AccountRepository;
import com.example.templatejava.accounts.BankingException;
import static com.example.templatejava.accounts.BankingException.Reason.*;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    public record Event(String type, String origin, String destination, Long amount) {}
    public record Result(Account origin, Account destination) {}
    private final AccountRepository accounts;
    public EventService(AccountRepository accounts) { this.accounts = accounts; }

    public Result execute(Event event) {
        if (event.type() == null || event.amount() == null || event.amount() <= 0) {
            throw new BankingException(INVALID_EVENT);
        }
        synchronized (accounts) {
            return switch (event.type()) {
                case "deposit" -> deposit(event);
                case "withdraw" -> withdraw(event);
                case "transfer" -> transfer(event);
                default -> throw new BankingException(INVALID_EVENT);
            };
        }
    }

    private Result deposit(Event event) {
        requireId(event.destination());
        Account account = accounts.findById(event.destination()).orElse(new Account(event.destination(), 0));
        if (event.amount() > Long.MAX_VALUE - account.balance()) throw new BankingException(DEPOSIT_FAILED);
        Account updated = new Account(account.id(), account.balance() + event.amount());
        accounts.save(updated);
        return new Result(null, updated);
    }

    private Result withdraw(Event event) {
        requireId(event.origin());
        Account account = accounts.findById(event.origin()).orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
        if (account.balance() < event.amount()) throw new BankingException(INSUFFICIENT_FUNDS);
        Account updated = new Account(account.id(), account.balance() - event.amount());
        accounts.save(updated);
        return new Result(updated, null);
    }

    private Result transfer(Event event) {
        requireId(event.origin());
        requireId(event.destination());
        if (event.origin().equals(event.destination())) throw new BankingException(TRANSFER_FAILED);
        Account origin = accounts.findById(event.origin()).orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
        Account destination = accounts.findById(event.destination()).orElse(new Account(event.destination(), 0));
        if (origin.balance() < event.amount()) throw new BankingException(INSUFFICIENT_FUNDS);
        if (event.amount() > Long.MAX_VALUE - destination.balance()) {
            throw new BankingException(TRANSFER_FAILED);
        }
        Account updatedOrigin = new Account(origin.id(), origin.balance() - event.amount());
        Account updatedDestination = new Account(destination.id(), destination.balance() + event.amount());
        accounts.save(updatedOrigin);
        accounts.save(updatedDestination);
        return new Result(updatedOrigin, updatedDestination);
    }

    private void requireId(String id) {
        if (id == null || id.isBlank()) throw new BankingException(INVALID_EVENT);
    }
}
