import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ContractPlan } from 'src/app/modele/contractPlan/contractPlan.modele';
import { ContractPlanService } from 'src/app/shared/contract-plan.service';

@Component({
  selector: 'app-liste-contract-plan',
  templateUrl: './liste-contract-plan.component.html',
  styleUrls: ['./liste-contract-plan.component.css']
})
export class ListeContractPlanComponent implements OnInit {
  list:any;
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  offers!: Observable<ContractPlan[]>;
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;
  tableSizes: any = [3, 6, 9, 12];

  constructor(private service :ContractPlanService ,private router : Router) { }

  ngOnInit(): void {
    this.service.getOffers().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
   
      }
          )
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }
  deleteContract = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.service.deleteContractPlan(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }
}
