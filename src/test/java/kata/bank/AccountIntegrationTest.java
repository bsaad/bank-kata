package kata.bank;

import kata.bank.domain.Account;
import kata.bank.domain.Operation;
import kata.bank.domain.exceptions.AccountNotFoundException;
import kata.bank.persistence.AccountRepository;
import kata.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static kata.bank.domain.OperationType.Deposit;
import static kata.bank.domain.OperationType.Withdrawal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AccountIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Test
    @Transactional
    void should_save_operations_and_balance() {
        Account account = new Account();

        account.processOperation(Deposit, new BigDecimal(100));

        account = accountRepository.save(account);

        account.processOperation(Withdrawal, new BigDecimal(70));

        account = accountRepository.save(account);

        assertThat(account.getBalance()).isEqualTo(new BigDecimal(30));

        List<Operation> operations = account.getOperations();
        assertThat(operations.size()).isEqualTo(2);

        assertThat(operations.get(0).getType()).isEqualTo(Deposit);
        assertThat(operations.get(0).getAmount()).isEqualTo(new BigDecimal(100));
        assertThat(operations.get(0).getBalance()).isEqualTo(new BigDecimal(100));
        assertThat(operations.get(0).getTime()).isNotNull();

        assertThat(operations.get(1).getType()).isEqualTo(Withdrawal);
        assertThat(operations.get(1).getAmount()).isEqualTo(new BigDecimal(70));
        assertThat(operations.get(1).getBalance()).isEqualTo(new BigDecimal(30));
        assertThat(operations.get(1).getTime()).isNotNull();

    }

    @Test
    void should_have_correct_balance_after_concurrent_updates() throws Exception {

        Account account = new Account();

        int nbThreads = 20;

        Long id = accountRepository.save(account).getId();

        ExecutorService executorService = Executors.newFixedThreadPool(nbThreads);

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nbThreads; i++) {
            futures.add(executorService.submit(() -> {
                try {
                    accountService.processOperation(id, Deposit, new BigDecimal(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get();
        }

        account = accountRepository.findById(id).get();
        assertThat(account.getBalance()).isEqualByComparingTo(new BigDecimal(nbThreads));
    }

    @Test
    void should_throw_exception_when_wrong_account() {

        assertThrows(AccountNotFoundException.class, () -> accountService.processOperation(77L, Withdrawal, new BigDecimal(200)));
    }
}
