package dev.budhi.service.impl;

import dev.budhi.dto.AccountDTO;
import dev.budhi.entity.Account;
import dev.budhi.repository.AccountRepository;
import dev.budhi.service.AccountService;
import dev.budhi.utilitis.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ValidationService validation;
    private final AccountRepository accountRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final ConcurrentHashMap<Long, Lock> lockMap = new ConcurrentHashMap<>();
    private final PlatformTransactionManager transactionManager;

    @Override
    public AccountDTO.AccountResponse transfer(AccountDTO.AccountRequest request) {
        validation.validate(request);

        Future<AccountDTO.AccountResponse> futureResponse = executorService.submit(() -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

            return transactionTemplate.execute(status -> {
                Account fromAccOpt = accountRepository.findByBankAndAccountNumber(request.getFromBank(), request.getFromAccount())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

                Account toAccOpt = accountRepository.findByBankAndAccountNumber(request.getToBank(), request.getToAccount())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

                executeTransferWithLocking(fromAccOpt, toAccOpt, request.getAmount());

                return AccountDTO.toAccountResponse(fromAccOpt, toAccOpt);
            });
        });

        try {
            return futureResponse.get(); // Waits for the task to complete and gets the result
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Transfer execution failed", e);
        }
    }

    private void executeTransferWithLocking(Account fromAcc, Account toAcc, BigDecimal amount) {
        Lock fromLock = lockMap.computeIfAbsent(fromAcc.getId(), k -> new ReentrantLock());
        Lock toLock = lockMap.computeIfAbsent(toAcc.getId(), k -> new ReentrantLock());

        fromLock.lock();
        try {
            toLock.lock();
            try {
                if (fromAcc.getBalance().compareTo(amount) >= 0) {
                    fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
                    toAcc.setBalance(toAcc.getBalance().add(amount));

                    accountRepository.save(fromAcc);
                    accountRepository.save(toAcc);
                } else {
                    throw new IllegalArgumentException("Insufficient funds");
                }
            } finally {
                toLock.unlock();
            }
        } finally {
            fromLock.unlock();
        }
    }
}
