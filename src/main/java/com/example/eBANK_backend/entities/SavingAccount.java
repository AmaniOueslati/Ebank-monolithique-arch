package com.example.eBANK_backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@DiscriminatorValue("SA") //compte epargne
public class SavingAccount extends  BankAccount{
    private double interest ;
}
