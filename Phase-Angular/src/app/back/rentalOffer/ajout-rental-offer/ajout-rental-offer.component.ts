import {HttpClient, HttpEventType} from "@angular/common/http";
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { catchError, throwError } from "rxjs";
//import { NgForm } from '@angular/forms';
import { RentalOffer } from 'src/app/modele/rentalOffer/rentaloffer.modele';
import { RentalofferserviceService } from 'src/app/shared/rentalofferservice.service';

@Component({
  selector: 'app-ajout-rental-offer',
  templateUrl: './ajout-rental-offer.component.html',
  styleUrls: ['./ajout-rental-offer.component.css']
})
export class AjoutRentalOfferComponent implements OnInit {

  constructor(private service: RentalofferserviceService , private httpClient: HttpClient,private routerNav: Router, private router : Router
    )  { }

  ngOnInit(): void {
  }
  retrievedImage: any;
  base64Data: any;
  retrieveResponse: any;
  message: any;

  //Gets called when the user selects an image
  //public onFileChanged(event :any) {
    //Select File
   // this.selectedFile = event.target.files[0];
  //}

  //Gets called when the user clicks on submit to upload the image



  rentalOffer: RentalOffer = new RentalOffer();
  id!: number;




  addRentallllOffer() {

    console.log('aaa',this.rentalOffer);
    this.service.addRentalOffer(this.rentalOffer, 2).subscribe(res=>{
      this.rentalOffer = new RentalOffer();
      this.routerNav.navigate(['/listRentalOffers'])

    });
  }

  save() {
    this.service
      .addRentalOffer(this.rentalOffer,2)
      .subscribe({
        next: data => {
          console.log("***********"+data);
          this.rentalOffer = new RentalOffer();
          this.gotoList();
          alert("L'offre est ajouté avec succée")
        },
        error: error => console.log(error)
      });
  }

  addRentalOffer() {
    this.service.addRentalOffer(this.rentalOffer, 2).pipe(
      catchError((error) => {
        // this.modalError();
        console.log(error)
        //  alert('Cette offre est non disponible dans cette date , vérifier la disponibilité ');
        alert('Offre ajouté avec succée!');
        return throwError(() => new Error(error));
      }),
    ).subscribe((res) => {
      alert('Offre ajouté avec succée!');

    });

  }

  gotoList() {
    this.router.navigate(['/listRentalOffers']);
  }


  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.rentalOffer.picture = file.name;
  }


  //onFileSelected(event: any) {

 // }


}
