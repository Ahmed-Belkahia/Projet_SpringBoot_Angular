import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SellContract } from 'src/app/modele/contract/contrat.modele';
import { ServiceContractService } from 'src/app/shared/service-contract.service';
@Component({
  selector: 'app-addcontract',
  templateUrl: './addcontract.component.html',
  styleUrls: ['./addcontract.component.css']
})
export class AddcontractComponent implements OnInit {

  constructor(private service:ServiceContractService, private route:ActivatedRoute, private router:Router) {
    this.idOffre = this.route.snapshot.params['id']
   }
   idOffre!:any;
   contract: SellContract=new SellContract();
  ngOnInit(): void {
  }
  async addContrat() {
    console.log('aaa', this.contract);
    
      await this.service.addContrat(this.contract, this.idOffre, 1).subscribe();
      this.contract = new SellContract();
      this.router.navigate(['/listecontract']);
     
    }
  
   
  

}
