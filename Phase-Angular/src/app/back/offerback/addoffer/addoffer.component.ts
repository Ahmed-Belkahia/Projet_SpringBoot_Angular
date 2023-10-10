import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';
import { TypeOffer } from 'src/app/modele/sale/typeoffer';
import { Router } from '@angular/router';
import { StatutOffre } from 'src/app/modele/sale/statutoffre';
import { Note } from 'src/app/modele/sale/note.modele';
import { ServiceContractService } from 'src/app/shared/service-contract.service';
import { SellContract } from 'src/app/modele/contract/contrat.modele';


@Component({
  selector: 'app-addoffer',
  templateUrl: './addoffer.component.html',
  styleUrls: ['./addoffer.component.css']
})
export class AddofferComponent implements OnInit {
 
 

  constructor(private service: ServiceOffreService,private service2:ServiceContractService, private router:Router) { }

  ngOnInit(): void {
  }
  Selleroffer: Saleoffer=new Saleoffer();
  note: Note=new Note();
  id!:number;
  selectedFile!: File;
  typesOffre = Object.values(TypeOffer);
  typessOffre = Object.values(StatutOffre);
  idOffre!:any;
 
  contract: SellContract=new SellContract();
  
  addoffer(){
    this.service.addoffer(this.Selleroffer,1).subscribe();
    this.Selleroffer=new Saleoffer();
    
    this.service2.addContrat(this.contract, this.idOffre, 1).subscribe();
    this.router.navigate(['/listeoffer']);
    
    }
    

  
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.Selleroffer.picture = file.name;
  };
  

}
