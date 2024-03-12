package com.example.eBANK_backend.dtos;

import com.example.eBANK_backend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class CurrentBankAccountDTO extends BankAccountDTO {

   private String rib ;
   private double balance ; //solde
   private Date createDate ;
   private AccountStatus status ;
   private CustomerDTO customerDTO ;
    private double overDraft ;
}
