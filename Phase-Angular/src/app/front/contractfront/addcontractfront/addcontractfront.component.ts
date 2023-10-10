import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SellContract } from 'src/app/modele/contract/contrat.modele';
import { ServiceContractService } from 'src/app/shared/service-contract.service';
@Component({
  selector: 'app-addcontractfront',
  templateUrl: './addcontractfront.component.html',
  styleUrls: ['./addcontractfront.component.css']
})
export class AddcontractfrontComponent implements OnInit {

  constructor(private service:ServiceContractService, private route:ActivatedRoute, private router:Router) {
    this.idOffre = this.route.snapshot.params['id']
   }
   showAddContractForm = false;
   idOffre!:any;
   contract: SellContract=new SellContract();
   i:any;
  ngOnInit(): void {
  }
   addContrat() {
    
    
       this.service.addContrat(this.contract, this.idOffre, 1).subscribe((contractsellid: number)=> {
        const url = 'updateoffre1back/' + contractsellid;
      this.router.navigateByUrl(url)
        
            });
            this.contract=new SellContract();}

}
