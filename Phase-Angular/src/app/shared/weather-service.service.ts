import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WeatherData } from '../modele/Weather.modele';

@Injectable({
  providedIn: 'root'
})
export class WeatherServiceService {
  private apiKey = '06f125b41c6f36dd0930ef4915e23f2f';
  private apiUrl = 'http://api.openweathermap.org/data/2.5/weather?q=';
  constructor(private http: HttpClient) { }

 
}
