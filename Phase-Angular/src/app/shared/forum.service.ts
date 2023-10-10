import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../modele/forum/comment.modele';
@Injectable({
  providedIn: 'root'
})
export class ForumService {

  constructor(private http: HttpClient) { }
  private BASE_URL = 'http://localhost:8000/PI/api/comment/comments';
  private BASE_URL_DELETE = 'http://localhost:8000/PI/api/comment/dComment'
  private BASE_URL_AJOUT ='http://localhost:8000/PI/api/comment/addComment'

  getOffers(): Observable<any> {
    return this.http.get(`${this.BASE_URL}`);
  }

  deleteOfferdeleteOffer(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL_DELETE}/${id}`, { responseType: 'text' });

  }
  addPlan(Comment: Comment): Observable<any> {
    
    return this.http.post<Comment>(`${this.BASE_URL_AJOUT}` ,Comment)
  }
}
