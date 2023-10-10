import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { Contrat } from '../modele/contract';

@Injectable({
  providedIn: 'root'
})
export class RentalContractServiceService {
  private BASE_URL_DELETE = 'http://localhost:8000/PI/rentalcontract/deleteRentalContract';
  private BASE_URL_REVENU_BY_OFFRE = 'http://localhost:8000/PI/rentaloffers/revenueoffer'
  private BASE_URL_IS_VALID = 'http://localhost:8000/PI/rentalcontract/ContractIsValidd';
  private BASE_URL_GET_BY_ID='http://localhost:8000/PI/rentalcontract/getRentalContract'
  constructor(
    private http : HttpClient
  ) { }

  getRentalContrat(){
    return this.http.get('http://localhost:8000/PI/rentalcontract/GetAllRentalContract')
  }
  getContratById(id :any){
    console.log('gg' , id)
    return this.http.get('http://localhost:8000/PI/rentalcontract/getRentalContract/'+id)
  }
  getUsersFinContrat(){
    return this.http.get('http://localhost:8000/PI/rentalcontract/GetUsersFinContrat')
  }

  getContratYoufaBaadTroisJoirs(id :number):Observable<any>{
    const requestOptions: Object = {
      /* other options here */
      responseType: 'text'
    }
    return this.http.get('http://localhost:8000/PI/rentalcontract/getContratYoufaBaadTroisJoirs/'+id,requestOptions);
  }
 
  
  addContrat(data : any , idOffre : any ,idUser : any){
    const requestOptions: Object = {
      /* other options here */
      responseType: 'text'
    }
    return this.http.post('http://localhost:8000/PI/rentalcontract/addRentalContract/'+idUser+'/'+idOffre , data ,requestOptions).pipe(
      catchError((error) => {
        // Handle error here
        console.log('Error:', error);
        return throwError('Something went wrong!');
      })
    );
  }
  deleteContrat(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL_DELETE}/${id}`, { responseType: 'text' });

  }

  contratIsValid(startdate : any,enddate:any): any {
    return this.http.get(`${this.BASE_URL_IS_VALID}/${startdate}/${enddate}`);
  }
  
  calculRevenu(id: number): Observable<number> {
    return this.http.get<number>(`${this.BASE_URL_REVENU_BY_OFFRE}/${id}`);
  }
  
}
