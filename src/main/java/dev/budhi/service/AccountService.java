package dev.budhi.service;

import dev.budhi.dto.AccountDTO;

public interface AccountService {

    AccountDTO.AccountResponse transfer(AccountDTO.AccountRequest request);

}
