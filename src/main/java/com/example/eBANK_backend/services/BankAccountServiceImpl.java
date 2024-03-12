package com.example.eBANK_backend.services;

import com.example.eBANK_backend.dtos.*;
import com.example.eBANK_backend.entities.*;
import com.example.eBANK_backend.enums.OperationType;
import com.example.eBANK_backend.exception.BalanceNotFoundException;
import com.example.eBANK_backend.exception.BankAccountNotFoundException;
import com.example.eBANK_backend.exception.CustomerNotFoundException;
import com.example.eBANK_backend.mappers.BankAccountMapperImp;
import com.example.eBANK_backend.repositories.AccountOperationRepository;
import com.example.eBANK_backend.repositories.BankAccountRepository;
import com.example.eBANK_backend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j // il ajoute un attribut log pour logger les messages
@Transactional @AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{
   // @Autowired
    private CustomerRepository customerRepository;
   // @Autowired
    private BankAccountRepository bankAccountRepository;
 //  @Autowired
    private AccountOperationRepository accountOperationRepository ;
    private BankAccountMapperImp dtoMapper ;


    // Logger log= LoggerFactory.getLogger(this.getClass().getName()); => @slf4j

    // AUTOWIRED EST DEPRECIER , ON PREFERE FAIRE L'INJECTION DE DEPENDANCES VIA LES CONSTRUCTEUR AVEC PARAMETRE OU BIEN AVEC ALL args constructor
//    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
//        this.customerRepository = customerRepository;
//        this.bankAccountRepository = bankAccountRepository;
//        this.accountOperationRepository = accountOperationRepository;
//    }


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerdto) {
        log.info("Saving new customer");
        Customer customer1 =dtoMapper.fromCustomerDTO(customerdto);
        Customer savedCustomer= customerRepository.save(customer1);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initilalBalanec, double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
    }


        CurrentAccount bankAccount=new CurrentAccount();

        bankAccount.setRib(UUID.randomUUID().toString());
        bankAccount.setCreateDate(new Date());
        bankAccount.setBalance(initilalBalanec);
        bankAccount.setCustomer(customer);
       bankAccount.setOverDraft(overdraft);
        CurrentAccount savedBankAccount= bankAccountRepository.save(bankAccount);

        return dtoMapper.fromCurrentBankAccount(savedBankAccount) ;

  }
   @Override
    public SavingBankAccountDTO saveSavingtBankAccount(double initilalBalanec, double interest, Long customerId) throws CustomerNotFoundException {

        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }


        SavingAccount bankAccount=new SavingAccount();

        bankAccount.setRib(UUID.randomUUID().toString());
        bankAccount.setCreateDate(new Date());
        bankAccount.setBalance(initilalBalanec);
      bankAccount.setCustomer(customer);
      bankAccount.setInterest(interest);
        SavingAccount savedBankAccount= bankAccountRepository.save(bankAccount);

       return dtoMapper.fromSavingBankAccount(savedBankAccount) ;
}


    @Override
    public List<CustomerDTO> listCustomers() {

        List<Customer> customers=customerRepository.findAll();
  /*     List<CustomerDTO>  customerDTOS=new ArrayList<>();
       for(Customer customer:customers){
           CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
           customerDTOS.add(customerDTO);
       } c'est la programmation imperative classique classique */
      List<CustomerDTO> customerDTOS=  customers.stream()
              .map(customer -> dtoMapper.fromCustomer(customer))
              .collect(Collectors.toList()); //programmation fonctionnelle en itulisant des streams
       return  customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException( "BankAccountNotFound"));
        if(bankAccount instanceof SavingAccount){

            SavingAccount savingAccount =(SavingAccount) bankAccount ;
            return dtoMapper.fromSavingBankAccount(savingAccount);
    }else{
        CurrentAccount currentAccount =(CurrentAccount)  bankAccount ;
       return dtoMapper.fromCurrentBankAccount(currentAccount);
   }
      // return bankAccount ;
    }

    @Override
    public void debit(String accountId, double amount, String discription) throws BankAccountNotFoundException, BalanceNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException( "BankAccountNotFound"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotFoundException("solde inssufisant");
        }
        else{

            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setType(OperationType.DEBIT);
            accountOperation.setAmount(amount);
            accountOperation.setDiscription(discription);
            accountOperation.setBankAccount(bankAccount);
            accountOperation.setOperationDate(new Date());
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepository.save(bankAccount);
        }

    }

    @Override
    public void credit(String accountId, double amount, String discription) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccountNotFound"));

        AccountOperation accountOperation=new AccountOperation();
            accountOperation.setType(OperationType.CREDIT);
            accountOperation.setAmount(amount);
            accountOperation.setDiscription(discription);
            accountOperation.setBankAccount(bankAccount);
            accountOperation.setOperationDate(new Date());
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()+amount);
            bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfer(String accountIdS, String accountIdD, double amount) throws BalanceNotFoundException, BankAccountNotFoundException {
        debit(accountIdS, amount, "Transfer to " + accountIdD);
        credit(accountIdD, amount, "Transfer from " + accountIdS);
    }


    @Override
    public List<BankAccountDTO> bankAccountlist(){
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
       List<BankAccountDTO> bankAccountsDTOS=bankAccounts.stream().map(bankAccount -> {
           if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAccount ;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            }
            else{
                CurrentAccount currentAccount=(CurrentAccount) bankAccount ;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountsDTOS;
   }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerdto) {
        log.info("Saving new customer");
        Customer customer1 =dtoMapper.fromCustomerDTO(customerdto);
        Customer savedCustomer= customerRepository.save(customer1);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
      List<AccountOperation> accountOperations=  accountOperationRepository.findByBankAccountRib(accountId);
     return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String rib, int page, int size) throws BankAccountNotFoundException {
     BankAccount bankAccount=bankAccountRepository.findById(rib).orElse(null);
     if(bankAccount==null)throw new BankAccountNotFoundException("Account not Found");
    // Page<AccountOperation> accountOperations=  accountOperationRepository.findByBankAccountRib(rib , (Pageable) PageRequest.of(page,size));
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountRibOrderByOperationDateDesc(rib, PageRequest.of(page, size));

        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
     //le travail suivant on peut le faire dans le mapper
      List<AccountOperationDTO> accountOperationsDTOS= accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
     accountHistoryDTO.setAccountOperationsDTO(accountOperationsDTOS);
     accountHistoryDTO.setAccountId(bankAccount.getRib());
     accountHistoryDTO.setBalance(bankAccount.getBalance());
     accountHistoryDTO.setCurrentPage(page);
     accountHistoryDTO.setPageSize(accountHistoryDTO.getPageSize());
     accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
      List<CustomerDTO> customerDTOS=  customers.stream().map(cus->dtoMapper.fromCustomer(cus)).collect(Collectors.toList());
      return customerDTOS;
    }


}
