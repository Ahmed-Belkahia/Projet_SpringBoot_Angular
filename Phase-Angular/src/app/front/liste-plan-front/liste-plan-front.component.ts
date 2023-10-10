import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Offer } from 'src/app/modele/offrePlan/offre.modele';
import { ServicePlanService } from 'src/app/shared/service-plan.service';

@Component({
  selector: 'app-liste-plan-front',
  templateUrl: './liste-plan-front.component.html',
  styleUrls: ['./liste-plan-front.component.css']
})
export class ListePlanFrontComponent implements OnInit {

  constructor(private service :ServicePlanService, private router : Router
    ) { }
  offers!: Observable<Offer[]>;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  pages: number = 1;
  planid!:any;

  ngOnInit(): void {
    this.service.getOffersnotnull().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
   
      }
      
          )
  }
  reloadData() {
    this.list = this.service.getOffers();
  }
  listcontractPlan(id:number){
    this.router.navigate(['/listfront', id])
  }

  

  
 

  filterD(){
    this.list = this.list.sort(function(a:any, b:any){
      return b.price - a.price
    })
  }
  filterA(){
    this.list = this.list.sort(function(a:any, b:any){
      return a.price - b.price
    })
  }
  
  detail(data:any){
   
  }
 
}
