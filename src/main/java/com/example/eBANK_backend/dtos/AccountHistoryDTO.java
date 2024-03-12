package com.example.eBANK_backend.dtos;

import lombok.Data;

import java.util.List;


@Data
public class AccountHistoryDTO {
   private List<AccountOperationDTO> accountOperationsDTO ;
   private String accountId ;

    private double balance ; //solde
    private String type ;
    private int totalPages;
     private int currentPage ;
     private int pageSize ;



}
