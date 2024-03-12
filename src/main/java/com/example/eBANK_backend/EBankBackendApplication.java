package com.example.eBANK_backend;

import com.example.eBANK_backend.dtos.BankAccountDTO;
import com.example.eBANK_backend.dtos.CurrentBankAccountDTO;
import com.example.eBANK_backend.dtos.CustomerDTO;
import com.example.eBANK_backend.dtos.SavingBankAccountDTO;
import com.example.eBANK_backend.entities.*;
import com.example.eBANK_backend.enums.AccountStatus;
import com.example.eBANK_backend.enums.OperationType;
import com.example.eBANK_backend.exception.CustomerNotFoundException;
import com.example.eBANK_backend.repositories.AccountOperationRepository;
import com.example.eBANK_backend.repositories.BankAccountRepository;
import com.example.eBANK_backend.repositories.CustomerRepository;
import com.example.eBANK_backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args-> {


			Stream.of("amani", "emna", "eya").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);

			});

			bankAccountService.listCustomers().forEach(cust -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 9000, 9000, cust.getId());
			        bankAccountService.saveSavingtBankAccount(Math.random() * 5000, 5.5, cust.getId());
					/*List<BankAccount> bankAccounts=bankAccountService.bankAccountlist();
					for(BankAccount bankAccount : bankAccounts){
						for(int i=0 ; i<10 ; i++){
							bankAccountService.credit(bankAccount.getRib(), 10000+Math.random()*1200000 , "credit");
							bankAccountService.debit(bankAccount.getRib(), 100+Math.random()*120, "credit");
						}*/
				} catch (CustomerNotFoundException e) {
					e.printStackTrace(); //afficher trace de l'exception
				}
			});
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountlist();
			for (BankAccountDTO bankAccount : bankAccounts) {
				for (int i = 0; i < 10; i++) {
					String accountId ;
					if(bankAccount instanceof SavingBankAccountDTO){
						accountId=((SavingBankAccountDTO) bankAccount).getRib();
					}else{
						accountId=((CurrentBankAccountDTO) bankAccount).getRib();
					}
					bankAccountService.credit(accountId, 10000 + Math.random() * 1200000, "credit");
					bankAccountService.debit(accountId, 100 + Math.random() * 120, "credit");
				};}

		};}

			}


