package com.example.eBANK_backend.entities;

import com.example.eBANK_backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // en cas de table per class on n'a plus besoin de la table banck account il nya que deux table SavingAccount and CurrentAccount alors on ajoute abstract si nn il va cree une table bankaccount car on est besoin soit compte epargne ou compte courant , abstract-> on creer des objet que des classe herite de classe mere
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" , length = 4 )
public /*abstract*/ class BankAccount {
    @Id
    private String rib ;
    private double balance ; //solde
    private Date createDate ;
    @Enumerated(EnumType.STRING)//il faut ajouter type enum afficher dans tableau si nn il va etre afficher pas 0 et si c'est ordinary va etre 0 et 1
    private AccountStatus status ;
    @ManyToOne
    private Customer customer ;
    //eager
    @OneToMany(mappedBy="bankAccount" , fetch=FetchType.LAZY)
    private List<AccountOperation> AccountOperations ;
}
