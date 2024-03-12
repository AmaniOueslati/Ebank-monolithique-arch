export interface AccountOperations {
    id:            number;
    operationDate: Date;
    amount:        number;
    type:          string;
    
    discription:   string;
}                    

export interface AccountDetails{
    
    accountId:            string;
    balance:              number;
    type:                 null;
    totalPages :  number ; 
    currentPage:          number;
    pageSize:             number;
    accountOperationsDTO: AccountOperations[];
}