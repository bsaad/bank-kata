package kata.bank.service;

import kata.bank.domain.Account;
import kata.bank.domain.OperationType;
import kata.bank.domain.exceptions.AccountNotFoundException;
import kata.bank.domain.exceptions.InsufficientBalanceException;
import kata.bank.persistence.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void processOperation(Long accountId, OperationType operationType, BigDecimal amount) throws AccountNotFoundException, InsufficientBalanceException {
        synchronized (accountId) {
            Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

            account.processOperation(operationType, amount);

            accountRepository.save(account);
        }
    }
}
