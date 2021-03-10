package kata.bank.persistence;

import kata.bank.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long>
{
}