import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';
import { TypeOffer } from 'src/app/modele/sale/typeoffer';
import { Route, Router } from '@angular/router';


@Component({
  selector: 'app-addofferfront',
  templateUrl: './addofferfront.component.html',
  styleUrls: ['./addofferfront.component.css']
})
export class AddofferfrontComponent implements OnInit {
 
 

  constructor(private service: ServiceOffreService,private route: Router ) { }

  ngOnInit(): void {
  }
  Selleroffer: Saleoffer=new Saleoffer();
  id!:number;
  selectedFile!: File;
  typesOffre = Object.values(TypeOffer);
  
  addoffer(){
    this.service.addoffer(this.Selleroffer,1).subscribe();
    this.Selleroffer=new Saleoffer();
    this.route.navigate(['/listeofferfront']);
    alert('le rendez_vous a ete ajouter avec succes')
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.Selleroffer.picture = file.name;
  };

}
