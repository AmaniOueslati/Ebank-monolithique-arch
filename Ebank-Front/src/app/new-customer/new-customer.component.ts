import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {
  newCustomerFormGroup !: FormGroup
  constructor(private fb:FormBuilder, private customerService : CustomerService , private router : Router) { }

  ngOnInit(): void {
    //je vais creer mon formulaire
    this.newCustomerFormGroup=this.fb.group({
      name:this.fb.control(null,[Validators.required , Validators.minLength(4)]),//attribut de formulaire et tpe
      email:this.fb.control(null , [Validators.email , Validators.required])
    }); 
  }
  handleSaveCustomer(){
    let customer:Customer=this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
       next:data=>{alert("Customer has been saved successfully !");
      // this.newCustomerFormGroup.reset();
    this.router.navigateByUrl("/customers");  },
       error:err =>{console.log("erreur")}
    });
  }

}
