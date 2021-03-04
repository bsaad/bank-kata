package kata.bank.domain;

import kata.bank.domain.exceptions.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kata.bank.domain.OperationType.Deposit;
import static kata.bank.domain.OperationType.Withdrawal;

public class Account {

    private final String id;
    private BigDecimal balance;
    private final List<Operation> operations;

    public Account(String id) {
        this.id = id;
        this.balance = new BigDecimal(0);
        this.operations = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        logOperation(Deposit, amount);
    }

    public void withdraw(BigDecimal amount) throws InsufficientBalanceException {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        balance = balance.subtract(amount);

        logOperation(Withdrawal, amount);
    }

    private void logOperation(OperationType type, BigDecimal amount) {
        operations.add(new Operation(type, amount, balance, LocalDateTime.now()));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
