import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ServicePlanService } from 'src/app/shared/service-plan.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'techmaster';
  list:any;
  constructor(private http: HttpClient, private service: ServicePlanService) {}
  ngOnInit() {
    
      
    }
  }
 



