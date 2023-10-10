import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { Offer } from '../modele/offrePlan/offre.modele';
import { UserService } from './user.service';
@Injectable({
  providedIn: 'root'
})
export class ServicePlanService {
  
  constructor(private http: HttpClient,private userService:UserService) { }
  private BASE_URL = 'http://localhost:8000/PI/api/plans/getPlan';
  private BASE_URL_DELETE ='http://localhost:8000/PI/api/plans/plan'
  private BASE_URL_AJOUT ='http://localhost:8000/PI/api/plans/addplan1'
  private apiUrl ='http://localhost:8000/PI/api/plans/addImage'
  private BASE_URL2 ='http://localhost:8000/PI/api/plans/add'
  private BASE_URL_REVENU ='http://localhost:8000/PI/api/plans/revenueoffer'
  private BASE_URL1 = 'http://localhost:8000/PI/api/plans/picture';
  
  getOffers(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`);
  }

  deleteOfferdeleteOffer(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL_DELETE}/${id}`, { responseType: 'text' });
  
  }

  addPlan(Offer: Offer): Observable<any> {
    const authToken = this.userService.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = { headers: headers };
    return this.http.post<Offer>(`${this.BASE_URL_AJOUT}` ,Offer,options)

  }

  getOffreById(id :number): Observable<Offer>{
    console.log('gg' , id)
    return this.http.get<Offer>('http://localhost:8000/PI/api/plans/'+id)
  }

  updateplan(data:any){
    return this.http.put('http://localhost:8000/PI/api/plans/updatePlan' ,data)
  }
 
  createPlan(Offer: Object): Observable<Object> {
    const authToken = this.userService.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = { headers: headers };
    return this.http.post(`${this.BASE_URL2}`, Offer);
  }

  calculRevenu(id: number): Observable<number> {
    return this.http.get<number>(`${this.BASE_URL_REVENU}/${id}`);
  }
  getOffersnotnull(): Observable<any> {
    return this.http.get(`${this.BASE_URL1}`);
  }
  

  
}
