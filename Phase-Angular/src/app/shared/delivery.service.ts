import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import axios from 'axios';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders,HttpClient } from '@angular/common/http';
import { Delivery } from '../modele/delivery/delivery.module';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  readonly  API_URL = 'http://localhost:8000/PI/api/deliverys';

  constructor(private httpClient: HttpClient,private userService: UserService) { }
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  getAllDelivery() {
    return this.httpClient.get(`http://localhost:8000/PI/api/deliverys`)
  }
  addDelivery(data : Delivery , idOffre : any ){
    console.log('USE URL' , data)
    const authToken = this.userService.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = { headers: headers };
    return this.httpClient.post('http://localhost:8000/PI/api/deliverys/test/'+idOffre , data,options)
  }
  getDeliveryById(id :any){
    console.log('gg' , id)
    return this.httpClient.get('http://localhost:8000/PI/api/deliverys/'+id)
  }
  EditDelivery(deliveryid : any,  data:any){
    return this.httpClient.put('http://localhost:8000/PI/api/deliverys/'+ deliveryid,data)
  }
  DelateDelivery(deliveryid : number){
    return  this.httpClient.delete(`${this.API_URL}/${deliveryid}`, { responseType: 'text' })
  }
  getBestDelivery() : Observable<string> {
    const url = `${this.API_URL}/BestDelivery`;
    return this.httpClient.get<string>(url);

  }
  
}
