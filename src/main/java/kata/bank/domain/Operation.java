package kata.bank.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private BigDecimal balance;
    private BigDecimal amount;
    private LocalDateTime time;

    public Operation() {
    }

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
