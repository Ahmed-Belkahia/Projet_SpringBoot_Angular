import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDatepickerInput } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { Console } from 'console';
import { catchError, throwError } from 'rxjs';
import { Contrat } from 'src/app/modele/contract';
import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';
import { RentalofferserviceService } from 'src/app/shared/rentalofferservice.service';

// import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-rental-contrat',
  templateUrl: './add-rental-contrat.component.html',
  styleUrls: ['./add-rental-contrat.component.css']
})
export class AddRentalContratComponent implements OnInit {

  contract = new Contrat()

  idOffre: any;
  today = new Date();
  month = this.today.getMonth();
  year = this.today.getFullYear();
  isValidd: any
  list: any
  datenow = new Date()
  dateValid: boolean = true
startdate!:any
enddate!:any
DateValidStartdateAndEnddate:boolean=true
DisabledBouton!:boolean
nbmonth!:any


  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private rentalService: RentalContractServiceService,
    private router: Router,
    private service: RentalofferserviceService,
  ) {
    this.idOffre = this.route.snapshot.params['id']
  }

  ngOnInit(): void {

    // this.isValid(1)
    this.rentalService.getRentalContrat().subscribe(
      (res) => {
        this.list = res;
        console.log(res);
      }

    )
  }

  
  /*
    addContrat(){
      console.log(this.contract)
      //-----------------http://localhost:8000/PI/rentalcontract/addRentalContract/2/50 => si id offre 50
      this.rentalService.addContrat(this.contract , this.idOffre , 2).subscribe(res=>{
        //----url http://localhost:8000/PI/rentalcontract/listeContrat/50 => bech yraj3ek lel lista mta offre id=50
        const url = '/listeContrat/'+this.idOffre
        this.router.navigateByUrl(url)
        console.log('addd' , res)
      })
    }*/



  addRentalContrat() {
    this.rentalService.addContrat(this.contract, this.idOffre, 2).
    subscribe((res) => {
      console.log("++++++++++++++",res)
      alert('Contrat ajouté avec succée!');

    },
    (error) => {
      console.log("Erreur lors de l'ajout du contrat:", error);
      alert("Une erreur s'est produite lors de l'ajout du contrat. Veuillez Vérifier la disponibilité");
    } 
     );

  }

  validationDate(event: any) {
    var dateAtt = new Date(event.target.value)
    this.startdate= new Date(event.target.value)
    var datefin=this.startdate.addMonthsToDate(this.nbmonth)
    console.log(datefin)
    var dateStatique="2043-06-15"
console.log(this.startdate)
    var a = this.datenow.getTime()
    var b = dateAtt.getTime()

    var x = event.target.value
    var y = dateStatique
    this.validation(x,dateStatique)
    console.log(a)
    console.log(b)
    if (b > a) {
      this.dateValid = false
    } else {
      this.dateValid = true
    }
  }



  validationnbMonth(event: any) {
    var nbmonth = event.target.value
    this.nbmonth=event.target.value
  console.log(this.nbmonth)
  this.dateIsValid()
console.log(this.DateValidStartdateAndEnddate)
  }







  disponile(data: any) {
    console.log(data.offreid)
    this.service.getDisponible(data.offreid).subscribe(res => {
      console.log(res)
      alert(res)
    }, err => {
      alert('error de serveur')
    })
  }

  addMonthsToDate(startDate: Date, monthsToAdd: number): Date {
    const newDate = new Date(startDate.getTime());
    newDate.setMonth(startDate.getMonth() + monthsToAdd);
    return newDate;
  }

dateIsValid():boolean{

  this.enddate= this.addMonthsToDate(this.startdate,this.nbmonth)
console.log("*****"+this.enddate)
  this.DateValidStartdateAndEnddate = this.rentalService.contratIsValid(this.startdate,this.enddate)
  console.log(this.DateValidStartdateAndEnddate)

  return this.rentalService.contratIsValid(this.startdate,this.enddate)

}

validation(startdatee:any,enddatee:any){
this.rentalService.contratIsValid(startdatee,enddatee).subscribe((res:any)=>{
console.log("*******"+res)
  if(res==true){
    this.DisabledBouton=false
  
}else {
  this.DisabledBouton=true
}
})
}
}
