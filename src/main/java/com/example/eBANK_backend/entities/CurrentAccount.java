package com.example.eBANK_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("CA") //compte courant
public class CurrentAccount extends BankAccount{
    private double overDraft;
}
