import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class NewsapiservicesService {
  constructor(private _http:HttpClient) { }
  //newsapiurl
  newsApiUrl = "https://newsapi.org/v2/everything?q=architecture%20houses&apiKey=a462d5e8b8764a51ae0adedd1ac5d882";
  // technewsapiurl
  techApiUrl = "https://newsapi.org/v2/everything?q=rent%20prices&apiKey=a462d5e8b8764a51ae0adedd1ac5d882";
    // businessnewsapiurl
    businessApiUrl = "https://newsapi.org/v2/everything?q=house%20prices&apiKey=a462d5e8b8764a51ae0adedd1ac5d882";

  //topheading()
  topHeading():Observable<any>
  {
    return this._http.get(this.newsApiUrl);
  }

  // technews()
  techNews():Observable<any>
  {
    return this._http.get(this.techApiUrl);
  }
// businssnews()
  businessNews():Observable<any>
  {
    return this._http.get(this.businessApiUrl);
  }

}