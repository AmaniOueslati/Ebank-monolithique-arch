package com.example.eBANK_backend.web;


import com.example.eBANK_backend.dtos.*;
import com.example.eBANK_backend.entities.BankAccount;
import com.example.eBANK_backend.exception.BalanceNotFoundException;
import com.example.eBANK_backend.exception.BankAccountNotFoundException;
import com.example.eBANK_backend.repositories.BankAccountRepository;
import com.example.eBANK_backend.services.BankAccountService;
import com.example.eBANK_backend.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService ;

    @GetMapping("/acounts/{rib}")
    public BankAccountDTO getBankAccount(@PathVariable(name ="rib")String accountId ) throws BankAccountNotFoundException {
       BankAccountDTO bankAccount= bankAccountService.getBankAccount(accountId);
       return bankAccount;

    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
    return bankAccountService.bankAccountlist();

    }

    @GetMapping("/accounts/{rib}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable(name ="rib")String rib){
        return bankAccountService.accountHistory(rib);

    }

    //http://localhost:8080/accounts/11a3e46a-1e55-4207-8da1-46e37fda3d7d/pageOperations?page=2
    @GetMapping("/accounts/{rib}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable(name ="rib")String rib ,
            @RequestParam(name="page" , defaultValue="0") int page ,
            @RequestParam(name="size" , defaultValue="5") int size ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(rib , page , size);

    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotFoundException, BankAccountNotFoundException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody transferRequestDTO transferRequestDTO) throws BalanceNotFoundException, BankAccountNotFoundException {
        this.bankAccountService.transfer(transferRequestDTO.getAccountSource() ,transferRequestDTO.getAccountDestination(), transferRequestDTO.getAmount());

    }
}
