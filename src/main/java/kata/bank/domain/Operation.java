package kata.bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Operation {
    private OperationType  type;
    private BigDecimal balance;
    private BigDecimal amount;
    private LocalDateTime time;

    public Operation(OperationType type, BigDecimal amount, BigDecimal balance, LocalDateTime time) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.time = time;
    }

    public OperationType getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
