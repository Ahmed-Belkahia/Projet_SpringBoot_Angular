import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';
import {jsPDF} from "jspdf"
import html2canvas from 'html2canvas'
import { Console } from 'console';
// import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-rental-contrat',
  templateUrl: './rental-contrat.component.html',
  styleUrls: ['./rental-contrat.component.css']
})
export class RentalContratComponent implements OnInit {
  idOffre : any
  contrats : any
  contratDetail : any
  show : boolean = false
revenu: any
  @ViewChild('pdf') pdf!: ElementRef;
  
  constructor(
    private route : ActivatedRoute,
    private rentalContratService : RentalContractServiceService,
    private router : Router,
    // private toastr : ToastrService
  ) { 
    this.idOffre = this.route.snapshot.params['id']
  }

  ngOnInit(): void {
    console.log("*****************************"+this.idOffre)

    this.getContrat()
    this.rentalContratService.calculRevenu(this.idOffre).subscribe(
      (response) => {
        this.revenu= response;
        console.log("**********************"+this.revenu)
      },
      (error) => {
        console.error(error);
      }
    ); 
   }

  goToAdd(){
    const url = 'listeContrat/'+this.idOffre+'/ajout'
    this.router.navigateByUrl(url)

  }

  getContrat(){
    this.rentalContratService.getRentalContrat().subscribe(res=>{
      this.contrats = res
      this.contrats = this.contrats.filter((i:any)=>{
        return i.rentalIOfferContractRental == this.idOffre
      })
      console.log(this.contrats)
    })
  }
  /*getRevenuByOffre(id:number){
    this.rentalContratService.calculRevenu(id).subscribe(res => {
this.revenu=res
    })
  }*/

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
          // this.toastr.success('Vous avez téléchargé le pdf avec succée', 'OK :)');
        })
      }
    })
  }
}
