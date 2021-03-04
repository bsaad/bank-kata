package kata.bank.domain;

import kata.bank.domain.exceptions.InsufficientBalanceException;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;

public class Account {

    private final String id;
    private BigDecimal balance;

    public Account(String id) {
        this.id = id;
        this.balance = new BigDecimal(0);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws InsufficientBalanceException {
        if(balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
