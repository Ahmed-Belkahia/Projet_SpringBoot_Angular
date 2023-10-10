import { Component, OnInit } from '@angular/core';
import { Offer } from 'src/app/modele/offrePlan/offre.modele';
import { ServicePlanService } from 'src/app/shared/service-plan.service';

@Component({
  selector: 'app-choice-plan',
  templateUrl: './choice-plan.component.html',
  styleUrls: ['./choice-plan.component.css']
})
export class ChoicePlanComponent implements OnInit {
  Offer: Offer = new Offer();
  id!: number;
  constructor(private service : ServicePlanService) { }

  ngOnInit(): void {
  }
  addPlanOffer() {
    this.service.addPlan(this.Offer).subscribe();
    this.Offer = new Offer(); 
  }

}
