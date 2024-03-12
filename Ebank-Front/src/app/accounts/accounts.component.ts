import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AccountsService } from '../services/accounts.service';
import { Observable, throwError } from 'rxjs';
import { AccountDetails } from '../models/account.model';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  AccountFormGroup !: FormGroup ;
  currentPage : number=0;
  pageSize : number =5 ;  
  accountObservable!: Observable<AccountDetails>
  operationFormGroup ! : FormGroup ; 
  errorMessage!: string ; 
  constructor( private fb : FormBuilder , private accountService: AccountsService) { }

  ngOnInit(): void {
  this.AccountFormGroup=this.fb.group({
    accountId : this.fb.control('')
  }); 
  this.operationFormGroup=this.fb.group({
   
   operationType:this.fb.control(null), 
   amount : this.fb.control(0),
   description : this.fb.control(null),
   accountDestination : this.fb.control(null)

  })
 

  }


  handleSearchAccount(){
   
   let accountId : string=this.AccountFormGroup.value.accountId; 
   this.accountObservable= this.accountService.getAccount(accountId , this.currentPage , this.pageSize).pipe(
    catchError(err=>{
      this.errorMessage=err.error.message
      return throwError(err);
    })
   ); 
  
  }
  gotoPage(page : number){
    this.currentPage=page; 
    this.handleSearchAccount(); 

  }
  handleAccountOperation(){
    let accountId : string = this.AccountFormGroup.value.accountId; 
    let operationType=this.operationFormGroup.value.operationType; 
    let amount : number= this.operationFormGroup.value.amount ; 
    let description : string = this.AccountFormGroup.value.description; 
    let accountDestination:string = this.AccountFormGroup.value.accountDestination

    if(operationType=='DEBIT'){
      this.accountService.debit(accountId , amount ,description ).subscribe({
        next: (data)=>{
          alert("Success Debit");
          this.operationFormGroup.reset(); 
          this.handleSearchAccount(); 
        }, 
      error : (err)=>{
        console.log(err); 
      }      }); 

    }else if(operationType=='CREDIT'){
      this.accountService.credit(accountId , amount ,description ).subscribe({
        next: (data)=>{
          alert("Success Credit");
          this.operationFormGroup.reset(); 
          this.handleSearchAccount(); 
        }, 
      error : (err)=>{
        console.log(err); 
      }      }); 

    }else if(operationType=='TRANSFER'){
      this.accountService.Transfer(accountId, accountDestination , amount ,description ).subscribe({
        next: (data)=>{
          alert("Success Transfer");
          this.operationFormGroup.reset(); 
          this.handleSearchAccount(); 
        }, 
      error : (err)=>{
        console.log(err); 
      }      }); 

    }
   
  }

}
