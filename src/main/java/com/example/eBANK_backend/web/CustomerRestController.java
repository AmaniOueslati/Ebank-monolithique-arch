package com.example.eBANK_backend.web;


import com.example.eBANK_backend.dtos.CustomerDTO;
import com.example.eBANK_backend.entities.Customer;
import com.example.eBANK_backend.exception.CustomerNotFoundException;
import com.example.eBANK_backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController{
   private BankAccountService bankAccountService ;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return  bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> Serachcustomers(@RequestParam(name="keyword" , defaultValue = "") String keyword){
        return  bankAccountService.searchCustomers("%"+keyword+"%");
    }

    @GetMapping("/customers/{id}")
    public  CustomerDTO getCustomer(@PathVariable(name ="id") Long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO=bankAccountService.getCustomer(customerId);
        return  customerDTO;


    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
     return bankAccountService.saveCustomer(customerDTO) ;
    }



    //mise a jour avec put
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name ="id") Long customerId ,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
       return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable(name ="id") Long id ){
        bankAccountService.deleteCustomer(id);

    }
}
