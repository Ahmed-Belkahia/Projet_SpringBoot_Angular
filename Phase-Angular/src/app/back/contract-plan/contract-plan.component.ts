import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContractPlanService } from 'src/app/shared/contract-plan.service';
import {jsPDF} from "jspdf"
import html2canvas from 'html2canvas'

import { ServicePlanService } from 'src/app/shared/service-plan.service';

@Component({
  selector: 'app-contract-plan',
  templateUrl: './contract-plan.component.html',
  styleUrls: ['./contract-plan.component.css']
})
export class ContractPlanComponent implements OnInit {
  list:any;
  idOffre : any
  contrats : any
  contratDetail : any
  show : boolean = false
  @ViewChild('pdf') pdf!: ElementRef;

  constructor(private service : ContractPlanService, private router :Router, private route: ActivatedRoute, private servicePlan : ServicePlanService) { 

    this.idOffre = this.route.snapshot.params['id']

}

  getContrat(){
    this.service.getOffers().subscribe(res=>{
      this.contrats = res
      this.contrats = this.contrats.filter((i:any)=>{
        return i.planContractPlan == this.idOffre
      })
      console.log(this.contrats)
    })
  }

  revenu! :any;


  ngOnInit(): void {
    this.getContrat()
    console.log(this.idOffre)

    this.servicePlan.calculRevenu(this.idOffre).subscribe(
      (response) => {
        this.revenu= response;
        console.log("********************"+this.revenu)
      },
      (error) => {
        console.error(error);
      }
    );

  }

  deleteOffer = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.service.deleteContractPlan(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }

  generatePDF(item:any){
    console.log(item)
    this.show = true
    this.contratDetail = item
  }

  loadPdfff(){
    const op = {
      backgroud : 'white',
      scale : 3
    }

    var div = document.getElementById('pdff')

    setTimeout(()=>{
      if(this.pdf){

        html2canvas(this.pdf.nativeElement , op).then(async canvas=>{
          const myImage = await canvas.toDataURL('image/png')
          let myPdf = new jsPDF('p' , 'mm' ,'a4')
          var width = await myPdf.internal.pageSize.getWidth()
          var heigth = await canvas.height * width / canvas.width
          myPdf.addImage(myImage , 'PNG' , 0 , 0 , width , heigth)
          myPdf.save('contrat.pdf')
          // this.toastr.success('Vous avez téléchargé le pdf avec succée', 'OK 🙂');
        })
      }
    })
  }

  goToAdd(){

    const url = 'listeContratplan/'+this.idOffre+'/ajout'

    this.router.navigateByUrl(url)

  }
}
