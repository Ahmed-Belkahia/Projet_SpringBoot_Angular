import { Component, OnInit } from '@angular/core';

import { WeatherServiceService } from 'src/app/shared/weather-service.service';

@Component({
  selector: 'app-weather-widegt',
  templateUrl: './weather-widegt.component.html',
  styleUrls: ['./weather-widegt.component.css']
})
export class WeatherWidegtComponent implements OnInit {
  WeatherData:any;
  constructor() { }
  ngOnInit() {
    this.WeatherData = {
      main : {},
      isDay: true
    };
    this.getWeatherData();
  }
  cityName: string = 'mumbai'; // default city name

  getWeatherData(){
    fetch(`https://api.openweathermap.org/data/2.5/weather?q=${this.cityName}&appid=ff1bc4683fc7325e9c57e586c20cc03e`)
    .then(response=>response.json())
    .then(data=>{this.setWeatherData(data);})
  }
  

  setWeatherData(data: any){
    this.WeatherData = data;
    let sunsetTime = new Date(this.WeatherData.sys.sunset * 1000);
    this.WeatherData.sunset_time = sunsetTime.toLocaleTimeString();
    let currentDate = new Date();
    this.WeatherData.isDay = (currentDate.getTime() < sunsetTime.getTime());
    this.WeatherData.temp_celcius = (this.WeatherData.main.temp - 273.15).toFixed(0);
    this.WeatherData.temp_min = (this.WeatherData.main.temp_min - 273.15).toFixed(0);
    this.WeatherData.temp_max = (this.WeatherData.main.temp_max - 273.15).toFixed(0);
    this.WeatherData.temp_feels_like = (this.WeatherData.main.feels_like - 273.15).toFixed(0);
  }
}
