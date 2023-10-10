import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Offer } from 'src/app/modele/offrePlan/offre.modele';
import { ServicePlanService } from 'src/app/shared/service-plan.service';


@Component({
  selector: 'app-addplan',
  templateUrl: './addplan.component.html',
  styleUrls: ['./addplan.component.css']
})
export class AddplanComponent implements OnInit {
  title!: string;
  image!: File;
  price!: number;
  realizationprice!: number;
  livingroom!: number;
  kitchen!: number;
  wc!: number;
  room1!: number;
  room2!: number;
  description!: string;
  iduser!: number;
  constructor(private service :ServicePlanService, private router : Router) { }

  ngOnInit(): void {
  }
  Offer: Offer = new Offer();
  id!: number;
  selectedFile!: File;
  userFile!:any;
  imgURL!:any;
  
  addPlanOffer() {
    this.service.addPlan(this.Offer).subscribe();
    this.Offer = new Offer(); 
  }
  
  save() {
    this.service
      .createPlan(this.Offer)
      .subscribe({
        next: data => {
          console.log(data);
          this.Offer = new Offer();
          this.gotoList();
        },
        error: error => console.log(error)
      });
  }
  gotoList() {
    this.router.navigate(['/listOffers']);
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.Offer.picture = file.name;
  }
}
