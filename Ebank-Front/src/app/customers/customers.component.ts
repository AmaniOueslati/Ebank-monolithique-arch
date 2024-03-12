import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../services/customer.service';
import { Customer } from '../models/customer.model';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { FormBuilder, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
customers! : Observable<Array<Customer>> ; 
SearchformGroup: FormGroup | undefined ;
errorMessage!: String; 
  constructor(private customerService:CustomerService ,private  fb:FormBuilder) { }

  ngOnInit(): void {
   this.customers=this.customerService.getCustomers().pipe(
    catchError(err=>{
      this.errorMessage=err.message; 
       return throwError(err);
    })
   ); 


   this.SearchformGroup=this.fb.group({
    keyword: this.fb.control("")

   })
    }
    handleSearchCustomers(){
      let kw=this.SearchformGroup?.value.keyword ; 
      this.customers=this.customerService.searchCustomers(kw).pipe(
        catchError(err=>{
          this.errorMessage=err.message; 
           return throwError(err);
        })); 

    }
    handleDeletCustomer(c:Customer){
      let conf=confirm("Are you sure ?"); 
      if(!conf) return ; 
      this.customerService.deleteCustomer(c.id).subscribe({
        next:resp=>{
          this.customers=this.customers.pipe(
            map( data =>
              { let index=data.indexOf(c);
                data.slice(index,1)
              return data ; })
          );
        },
        error:err=>{console.log(err);}
      })
    }
 
  }


