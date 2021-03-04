package kata.bank.domain;

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

    public BigDecimal getBalance() {
        return balance;
    }
}
