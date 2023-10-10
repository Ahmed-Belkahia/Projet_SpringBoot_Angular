import { Component, OnInit } from '@angular/core';
import { SellContract } from 'src/app/modele/contract/contrat.modele';
import { ServiceContractService } from 'src/app/shared/service-contract.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-listecontract',
  templateUrl: './listecontract.component.html',
  styleUrls: ['./listecontract.component.css']
})
export class ListecontractComponent implements OnInit {
  idOffre : any
  offers!: Observable<SellContract[]>;
  sellContracts: SellContract[] = [];
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 15;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  contrats : any 

  constructor(private service : ServiceContractService, private router:Router , private route : ActivatedRoute) { 
  
    this.idOffre = this.route.snapshot.params['id']
  }

  ngOnInit(): void {
    this.service.getAllContract().subscribe(
      (res) => {
        this.list = res;
        console.log(res);}
    )}
    onTableDataChange(event: any) {
      this.page = event;
      this.ngOnInit();
    }
    
    
  }


