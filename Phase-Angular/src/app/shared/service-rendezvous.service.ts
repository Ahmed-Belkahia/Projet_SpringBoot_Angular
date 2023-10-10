import { Injectable } from '@angular/core';
import { Rendezvous } from '../modele/sale/rendezvous.modele';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceRendezvousService {

  constructor(private http:HttpClient) { }

  
  private url_getAll = 'http://localhost:8000/PI/api/seller/AllContrat';
  private url_delete = 'http://localhost:8000/PI/api/seller/deleteOffer';
  private url_update='http://localhost:8000/PI/api/seller/updateOffer';

  demanderRendezVous(data : Rendezvous , idOffre : any ){
    console.log('USE URL' , data)
    return this.http.post('http://localhost:8000/PI/api/seller/demanderRendezVous/'+ idOffre , data)
  }

  
}
