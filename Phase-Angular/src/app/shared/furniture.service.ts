import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import axios from 'axios';
import { Observable } from 'rxjs/internal/Observable';
import { Furniture } from 'src/app/modele/furniture';

@Injectable({
  providedIn: 'root'
})
export class FurnitureService {
  readonly apiUrl = 'http://localhost:8000/PI/api/furnitures';
  readonly apiUrladd = 'http://localhost:8000/PI/api/furnitures/addfurniture';

  BasedUrl : string;
  constructor(private http: HttpClient) {
    this.BasedUrl = 'http://localhost:8000/PI/api/furnitures';
  }
   cors = require('cors');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
   getAllFurniture(){
    return this.http.get(`${this.apiUrl}`)

  }

  addFurniture(Offer: any, id:number): Observable<any> {
    return this.http.post<Furniture>(`${this.apiUrladd}/${id}` ,Offer)
  }
  
  
  getRelocationById(id :any){
    console.log('gg' , id)
    return this.http.get('http://localhost:8000/PI/api/furnitures/'+id)
  }

  EditFurniture(relocationid : any,  data:any){
    return this.http.put('http://localhost:8000/PI/api/furnitures/'+ relocationid,data)
  }

  deleteFurniture(idProduct : any){
    return  this.http.delete(`${this.apiUrl}/${idProduct}`, { responseType: 'text' });
  }
}
