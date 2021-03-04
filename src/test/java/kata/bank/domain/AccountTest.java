package kata.bank.domain;

import kata.bank.domain.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    public void should_update_balance_after_deposit() {

        // Given
        Account account = new Account("1");

        // When
        account.deposit(new BigDecimal(50));
        account.deposit(new BigDecimal(70));

        // Then
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(120));
    }

    @Test
    public void should_update_balance_after_withdrawal() throws InsufficientBalanceException {

        // Given
        Account account = new Account("1");

        // When
        account.deposit(new BigDecimal(100));
        account.withdraw(new BigDecimal(70));

        // Then
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(30));
    }

    @Test
    public void should_fail_withdrawal_when_insufficient_balance() {

        // Given
        Account account = new Account("1");
        account.deposit(new BigDecimal(100));

        // Then
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(new BigDecimal(200)));
    }

}