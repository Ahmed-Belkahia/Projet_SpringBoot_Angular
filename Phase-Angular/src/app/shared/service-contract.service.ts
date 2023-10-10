import { Injectable } from '@angular/core';
import { SellContract } from '../modele/contract/contrat.modele';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceContractService {

  constructor(private http:HttpClient) { }

  
  private url_getAll = 'http://localhost:8000/PI/api/seller/AllContrat';
  private url_delete = 'http://localhost:8000/PI/api/seller/deleteOffer';
  private url_update='http://localhost:8000/PI/api/seller/updateOffer';

  addContrat(data : SellContract , idOffre : any ,idUser : any):Observable<number>{
   
    return this.http.post<number>('http://localhost:8000/PI/api/seller/addContrat/'+idUser+'/'+idOffre , data)
  }
  getAllContract(): Observable<any> {
    return this.http.get(`${this.url_getAll}`);
  }
  
}
