import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Post } from '../modele/forum/post';
import { UserService } from './user.service';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class ServicePostService {



  constructor(private http: HttpClient, private userService:UserService) { }
  private baseUrl = 'http://localhost:8000/PI/api/post/posts?size=100';
  private BASE_URL_DELETE ='http://localhost:8000/PI/api/post/dPost'
  private BASE_URL_AJOUT ='http://localhost:8000/PI/api/post/addPost'
    // need to build URL based on category id


  getPostList(): Observable<any> {

    return this.http.get(`${this.baseUrl}`);
  }


  deletePost(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL_DELETE}/${id}`, { responseType: 'text' });

  }
  addPost(Post: Post){
    const authToken = this.userService.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = { headers: headers };
    return this.http.post<Post>(`${this.BASE_URL_AJOUT}` ,Post, options)
  }

  updatePost(data:any){
    return this.http.put('http://localhost:8000/PI/api/post/updatePost' ,data)
  }
  getOffreById(id :any){
    console.log('gg' , id)
    return this.http.get('http://localhost:8000/PI/api/post/getid/'+id)
  }
}
