import { Component, OnInit } from '@angular/core';
import { ServicePlanService } from 'src/app/shared/service-plan.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Offer } from 'src/app/modele/offrePlan/offre.modele';
@Component({
  selector: 'app-updateplan',
  templateUrl: './updateplan.component.html',
  styleUrls: ['./updateplan.component.css']
})
export class UpdateplanComponent implements OnInit {

  constructor(private service :ServicePlanService, private route: ActivatedRoute, private routerNav: Router) {
     this.id = this.route.snapshot.params['id'];
  this.service.getOffreById(this.id).subscribe(res => {
    console.log(res)
    this.obj = res
    this.rentalOffer = this.obj
    console.log('-----------', this.rentalOffer)})}
  id: any
  obj: any = {}
  rentalOffer: Offer = new Offer()

  ngOnInit(): void {
  }
  updateplan() {

    console.log(this.rentalOffer)
    this.service.updateplan(this.rentalOffer).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listOffers'])
    }, err => {
      this.routerNav.navigate(['/listOffers'])

    })
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.rentalOffer.picture = file.name;
  }
 
}
