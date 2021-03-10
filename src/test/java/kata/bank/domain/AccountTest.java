package kata.bank.domain;

import kata.bank.domain.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static kata.bank.domain.OperationType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    public void should_update_balance_after_deposit() throws InsufficientBalanceException {

        // Given
        Account account = new Account();

        // When
        account.processOperation(Deposit, new BigDecimal(50));
        account.processOperation(Deposit, new BigDecimal(70));

        // Then
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(120));
    }

    @Test
    public void should_update_balance_after_withdrawal() throws InsufficientBalanceException {

        // Given
        Account account = new Account();

        // When
        account.processOperation(Deposit, new BigDecimal(100));
        account.processOperation(Withdrawal, new BigDecimal(70));

        // Then
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(30));
    }

    @Test
    public void should_fail_withdrawal_when_insufficient_balance() throws InsufficientBalanceException {

        // Given
        Account account = new Account();
        account.processOperation(Deposit, new BigDecimal(100));

        // Then
        assertThrows(InsufficientBalanceException.class, () -> account.processOperation(Withdrawal, new BigDecimal(200)));
    }

    @Test
    public void should_log_operations() throws InsufficientBalanceException {
        // Given
        Account account = new Account();

        // When
        account.processOperation(Deposit, new BigDecimal(100));
        account.processOperation(Withdrawal, new BigDecimal(70));
        account.processOperation(Deposit, new BigDecimal(200));

        // Then
        List<Operation> operations = account.getOperations();
        assertThat(operations.size()).isEqualTo(3);

        assertThat(operations.get(0).getType()).isEqualTo(Deposit);
        assertThat(operations.get(0).getAmount()).isEqualTo(new BigDecimal(100));
        assertThat(operations.get(0).getBalance()).isEqualTo(new BigDecimal(100));
        assertThat(operations.get(0).getTime()).isNotNull();

        assertThat(operations.get(1).getType()).isEqualTo(Withdrawal);
        assertThat(operations.get(1).getAmount()).isEqualTo(new BigDecimal(70));
        assertThat(operations.get(1).getBalance()).isEqualTo(new BigDecimal(30));
        assertThat(operations.get(1).getTime()).isNotNull();

        assertThat(operations.get(2).getType()).isEqualTo(Deposit);
        assertThat(operations.get(2).getAmount()).isEqualTo(new BigDecimal(200));
        assertThat(operations.get(2).getBalance()).isEqualTo(new BigDecimal(230));
        assertThat(operations.get(2).getTime()).isNotNull();

    }

}