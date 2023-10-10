import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { Saleoffer } from '../modele/sale/saleoffer.modele';
import { TypeOffer } from '../modele/sale/typeoffer';
import { Note } from '../modele/sale/note.modele';

@Injectable({
  providedIn: 'root'
})
export class ServiceOffreService {

  constructor(private http: HttpClient) { }
  private url_ajout ='http://localhost:8000/PI/api/seller/addOffer';
  private url_getAll = 'http://localhost:8000/PI/api/seller/findAll';
  private url_delete = 'http://localhost:8000/PI/api/seller/deleteOffer';
  private url_update='http://localhost:8000/PI/api/seller/updateOffer';
  private BASE_URLFavorit='http://localhost:8000/PI/api/seller/favorite';
  private url_getAllf='http://localhost:8000/PI/api/seller/offers/interesse';
  private url_delete_favorit='http://localhost:8000/PI/api/seller/deleteOfferFavorit';
  private url_note='http://localhost:8000/PI/api/seller/donnerNote';
  private url_delete_note='http://localhost:8000/PI/api/seller/getMoyNote';
  private url_moyenne_note='http://localhost:8000/PI/api/seller/donnerNote';
  addoffer(Selleroffer: Saleoffer, id:number): Observable<any> {
    return this.http.post<Saleoffer>(`${this.url_ajout}/${id}` ,Selleroffer);
  }
  createNoteForOffer(note: Note, offreId: number,idUser: number): Observable<Note> {
    return this.http.post<Note>(`${this.url_note}/${offreId}/${idUser}`, note);
  }
 


  getAvgRatingForOffer(offreId: number): Observable<number> {
    return this.http.get<number>(`${this.url_note}${offreId}/avg-rating`);
  }
  getAllOffer(): Observable<any> {
    return this.http.get(`${this.url_getAll}`);
  }
  deleteOffer(id: number): Observable<any> {
    return this.http.delete(`${this.url_delete}/${id}`, { responseType: 'text' });
  
  }
  deleteOfferfavorit(id: number): Observable<any> {
    return this.http.delete(`${this.url_delete_favorit}/${id}`, { responseType: 'text' });
  
  }
  updateoffre(text:any){
    return this.http.put(this.url_update ,text);
  }
  getOffreById(id :any){
    console.log('..' , id)
    return this.http.get('http://localhost:8000/PI/api/seller/getbyId/'+id)
  }
  markOfferAsFavorie(idoffre: number) {
    const url = `${this.BASE_URLFavorit}/${idoffre}`;
    return this.http.put(url, {}).toPromise();
  }
  getallfavorit(): Observable<any> {
    return this.http.get(`${this.url_getAllf}`);
  }
  


}
