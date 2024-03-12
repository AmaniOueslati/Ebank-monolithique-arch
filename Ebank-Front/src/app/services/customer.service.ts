import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  url : string="http://localhost:8080"; 
  constructor(private http:HttpClient) { }
  public getCustomers(): Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.url+"/customers")
  }
  public searchCustomers(keyword:String) :  Observable<Array<Customer>>{
   return this.http.get<Array<Customer>>(this.url+"/customers/search?keyword="+keyword)
  }

  public saveCustomer(customer:Customer) :  Observable<Customer>{
    return this.http.post<Customer>(this.url+"/customers" , customer);
   }
   public deleteCustomer(id:number) {
    return this.http.delete<Customer>(this.url+"/customers/"+id);
   }
}
