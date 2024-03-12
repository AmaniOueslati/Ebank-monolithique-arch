package com.example.eBANK_backend.services;

import com.example.eBANK_backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BankService {
    @Autowired
    BankAccountRepository bankAccountRepository;

}
