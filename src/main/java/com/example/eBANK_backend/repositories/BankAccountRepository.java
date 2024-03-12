package com.example.eBANK_backend.repositories;

import com.example.eBANK_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount , String> {
}
