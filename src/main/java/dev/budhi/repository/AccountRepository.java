package dev.budhi.repository;

import dev.budhi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByBankAndAccountNumber(String bank, String accountNumber);

}
