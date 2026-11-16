package com.example.templatejava.accounts;

public class BankingException extends RuntimeException {
    public enum Reason { INVALID_EVENT, ACCOUNT_NOT_FOUND, INSUFFICIENT_FUNDS, DEPOSIT_FAILED, WITHDRAW_FAILED, TRANSFER_FAILED }
    private final Reason reason;

    public BankingException(Reason reason) {
        this.reason = reason;
    }

    public Reason reason() { return reason; }
}
