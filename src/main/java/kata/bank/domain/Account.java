package kata.bank.domain;

import kata.bank.domain.exceptions.InsufficientBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal balance;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<Operation> operations;

    public Account() {
        this.balance = new BigDecimal(0);
        this.operations = new ArrayList<>();
    }

    public void processOperation(OperationType operationType, BigDecimal amount) throws InsufficientBalanceException {
        switch (operationType) {
            case Deposit:
                balance = balance.add(amount);
                break;
            case Withdrawal:
                if (balance.compareTo(amount) < 0) {
                    throw new InsufficientBalanceException();
                }
                balance = balance.subtract(amount);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operationType);
        }

        logOperation(operationType, amount);
    }

    private void logOperation(OperationType type, BigDecimal amount) {
        operations.add(new Operation(type, amount, balance, LocalDateTime.now()));
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
