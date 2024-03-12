package com.example.eBANK_backend.dtos;

import lombok.Data;

@Data
public class transferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount ;
    private String description ;
}
