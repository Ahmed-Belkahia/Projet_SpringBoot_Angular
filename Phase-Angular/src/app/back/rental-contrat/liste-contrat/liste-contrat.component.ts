import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Contrat } from 'src/app/modele/contract';
import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';

@Component({
  selector: 'app-liste-contrat',
  templateUrl: './liste-contrat.component.html',
  styleUrls: ['./liste-contrat.component.css']
})
export class ListeContratComponent implements OnInit {


  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  offers!: Observable<Contrat[]>;
  list: any;
  
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];

  constructor(
    private service: RentalContractServiceService,
    private router : Router
    ) { }

  ngOnInit(): void {
    this.service.getRentalContrat().subscribe(
      (res) => {
        this.list = res;
        console.log(res);
      }

    )
  }
  reloadData() {
    this.list = this.service.getRentalContrat();
  }




  deleteContrat = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.service.deleteContrat(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
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

  update(i: any) {
    console.log('update', i)
    this.router.navigate(['/UpdateRentalOffer',i.offreid])
  }

  detail(data:any){
    console.log(data)
    const url = 'listeContrat/'+data.offreid
    this.router.navigateByUrl(url)
  }

 

}
