import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContractPlan } from 'src/app/modele/contractPlan/contractPlan.modele';
import { ContractPlanService } from 'src/app/shared/contract-plan.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-contract-plan',
  templateUrl: './add-contract-plan.component.html',
  styleUrls: ['./add-contract-plan.component.css']
})
export class AddContractPlanComponent implements OnInit {

  constructor(private service : ContractPlanService, private route : ActivatedRoute,  private router : Router) {
    this.idOffre = this.route.snapshot.params['id']
}
  idOffre!:any;
  ngOnInit(): void {
  }
  contract = new ContractPlan()

  async addPlanContrat() {
    console.log('aaa', this.contract);
    try {
      await this.service.addContrat(this.contract, this.idOffre).toPromise();
      this.contract = new ContractPlan();
  
      // show success notification
      
    } catch (error) {
      // show error alert
      this.alertError();
    }
  }

  successNotification() {
    Swal.fire('Hi', 'We have been informed!', 'success');
  }

  alertError() {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'veuiller proceder au paiement ',
    });
  }
  tinyAlert() {
    Swal.fire('Hey there!');
  }
  

  
}
