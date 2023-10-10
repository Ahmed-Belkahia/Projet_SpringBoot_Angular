import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Payment } from '../modele/Payment/payment.modele';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8000/PI/api/deliverys/charge'; // Remplacer avec l'URL de votre serveur Spring
  readonly delurl="http://localhost:8000/PI/api/deliverys/factures/"
  private apiUrl1 = 'http://localhost:8000/PI/api/deliverys/getpayement';

  constructor(private http: HttpClient, private userService : UserService) { }
 
  createChargeplan(token: string, currency: string, contractId: number): Observable<any> {
    const url = 'http://localhost:8000/PI/api/contractPlans/charge?token='+token+'&currency='+currency+'&id='+contractId;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers: headers };
    return this.http.post<any>(url, options);
  }
  
  createCharge(token: string, currency: string, contractId: number): Observable<any> {
    const url = 'http://localhost:8000/PI/api/deliverys/charge?token='+token+'&currency='+currency+'&iddelevry='+contractId;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers: headers };
    return this.http.post<any>(url, options);
  }


  createChargetax(token: string, currency: string,id : number): Observable<any> {
    const url = 'http://localhost:8000/PI/api/taxs/charge?token='+token+'&currency='+currency+'&id='+id;
    const authToken = this.userService.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = { headers: headers };
    return this.http.post<any>(url, options);
  }

  telechargerFacturePDF(id: number) {
    const url = '${this.delurl}${id}';
    return this.http.get(url, { responseType: 'arraybuffer' });
  }

  getAllPayments(): Observable<any> {
    return this.http.get(`${this.apiUrl1}`);
  }
  createCharge1(token: string, currency: string, contractId: number): Observable<any> {
    const url = 'http://localhost:8000/PI/api/seller/charge?token='+token+'&currency='+currency+'&id='+contractId;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers: headers };
    return this.http.post<any>(url, options);
  }
  
}