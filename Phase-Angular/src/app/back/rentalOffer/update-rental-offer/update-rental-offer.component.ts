import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RentalOffer } from 'src/app/modele/rentalOffer/rentaloffer.modele';
import { RentalofferserviceService } from 'src/app/shared/rentalofferservice.service';

@Component({
  selector: 'app-update-rental-offer',
  templateUrl: './update-rental-offer.component.html',
  styleUrls: ['./update-rental-offer.component.css']
})
export class UpdateRentalOfferComponent implements OnInit {

  id: any
  obj: any = {}
  rentalOffer: RentalOffer = new RentalOffer()

  constructor(private service: RentalofferserviceService, private route: ActivatedRoute, private routerNav: Router
  ) {
    this.id = this.route.snapshot.params['idOffre'];
    this.service.getOffreById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.rentalOffer = this.obj
      console.log('-----------', this.rentalOffer)

    })
  }

  ngOnInit(
  ): void {
    console.log(this.rentalOffer)

  }

  UpdateRentallllOffer() {

    console.log(this.rentalOffer)
    this.service.updateOffre(this.rentalOffer).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listRentalOffers'])
    }, err => {
      this.routerNav.navigate(['/listRentalOffers'])

    })
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.rentalOffer.picture = file.name;
    console.log("+++++++++++"+ this.rentalOffer.picture)
  }

}
