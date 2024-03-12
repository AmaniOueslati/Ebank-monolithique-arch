package com.example.eBANK_backend.services;

import com.example.eBANK_backend.dtos.*;
import com.example.eBANK_backend.entities.BankAccount;
import com.example.eBANK_backend.entities.CurrentAccount;
import com.example.eBANK_backend.entities.Customer;
import com.example.eBANK_backend.entities.SavingAccount;
import com.example.eBANK_backend.exception.BalanceNotFoundException;
import com.example.eBANK_backend.exception.BankAccountNotFoundException;
import com.example.eBANK_backend.exception.CustomerNotFoundException;

import java.time.temporal.ChronoUnit;
import java.util.List;

public interface BankAccountService {
     CustomerDTO saveCustomer(CustomerDTO customer);
     CurrentBankAccountDTO saveCurrentBankAccount(double initilalBalanec , double overdraft, Long customerId) throws CustomerNotFoundException;
     SavingBankAccountDTO saveSavingtBankAccount(double initilalBalanec , double intrestrate, Long customerId) throws CustomerNotFoundException;
     List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId, double amount , String description) throws BankAccountNotFoundException, BalanceNotFoundException;
     void credit(String accountId, double amount , String description) throws BankAccountNotFoundException;
     void transfer(String accountIdS , String accountIdD , double amount) throws BalanceNotFoundException, BankAccountNotFoundException;

     List<BankAccountDTO> bankAccountlist();

     CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

     CustomerDTO updateCustomer(CustomerDTO customerdto);

     void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String rib, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
