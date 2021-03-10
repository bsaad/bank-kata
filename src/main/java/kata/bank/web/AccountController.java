package kata.bank.web;

import kata.bank.service.AccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController("account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
}
