import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountDetails } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  url : string="http://localhost:8080"; 

  constructor( private  http : HttpClient ) { }
  public getAccount(accountId : string , page:number , size :number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(this.url+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size); 
  }
  public debit(accountId : string , amount : number , description : string){
 
    let data={accounId : accountId , amount : amount , description:description}
    return this.http.post(this.url+"/accounts/debit",data); 
  }

  public credit(accountId : string , amount : number , description : string){
 
    let data={accounId : accountId , amount : amount , description:description}
    return this.http.post(this.url+"/accounts/credit",data); 
  }
  public Transfer(accountSource: string , accountDestination: string  , amount : number , description : string){
 
    let data={accountSource, accountDestination,amount ,description}
    return this.http.post(this.url+"/accounts/transfer",data); 
  }
}
