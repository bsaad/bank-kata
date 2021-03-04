package kata.bank.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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

}