package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private AccountRepository accountRepository;

    public Optional<Account> findByEmail(String accountEmail) {
        return accountRepository.findByEmail(accountEmail);
    }
}
