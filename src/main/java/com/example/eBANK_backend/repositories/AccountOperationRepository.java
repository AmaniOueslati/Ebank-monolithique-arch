package com.example.eBANK_backend.repositories;

import com.example.eBANK_backend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    public List<AccountOperation> findByBankAccountRib(String rib);
    public Page<AccountOperation> findByBankAccountRibOrderByOperationDateDesc(String rib, Pageable pageable);
}
