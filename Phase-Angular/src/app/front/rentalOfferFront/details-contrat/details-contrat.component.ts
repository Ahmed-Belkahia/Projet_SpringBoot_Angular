import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Contrat } from 'src/app/modele/contract';
import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';

@Component({
  selector: 'app-details-contrat',
  templateUrl: './details-contrat.component.html',
  styleUrls: ['./details-contrat.component.css']
})
export class DetailsContratComponent implements OnInit {
id:any
contrat:any

  constructor(private service: RentalContractServiceService, private route: ActivatedRoute, private routerNav: Router) {
    this.id = this.route.snapshot.params['id'];

   }
  ngOnInit(): void {
   this.contrat= this.service.getContratById(this.id).subscribe(res =>
    {
      this.contrat=res ;
      console.log("ùùùùùù"+res) ;

    } )
;
  }



  
}
