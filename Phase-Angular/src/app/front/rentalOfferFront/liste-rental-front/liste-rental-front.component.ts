import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { RentalOffer } from 'src/app/modele/rentalOffer/rentaloffer.modele';
import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';
import { RentalofferserviceService } from 'src/app/shared/rentalofferservice.service';

@Component({
  selector: 'app-liste-rental-front',
  templateUrl: './liste-rental-front.component.html',
  styleUrls: ['./liste-rental-front.component.css']
})
export class ListeRentalFrontComponent implements OnInit {

  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  offers!: Observable<RentalOffer[]>;
  list: any;
  date1:any
  date2:any
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  listAvailableOffers: any;
contratYoufewBaadTroisJours!:any
  idOffre: any;
  today = new Date();
  month = this.today.getMonth();
  year = this.today.getFullYear();
  datePickerForm: FormGroup;

  constructor(    private formBuilder: FormBuilder,
    private service: RentalofferserviceService,    private serviceContrat: RentalContractServiceService,

    private router : Router
    ) { }

  ngOnInit(): void {

    this.service.getOffers().subscribe(
      (res) => {
        this.list = res;
        console.log(res);
      })

    this.datePickerForm = this.formBuilder.group({
      startdate: [''],
      enddate: ['']
    });
    this.datePickerForm.valueChanges.subscribe((val) => {
      console.log(val)
    });



  
  }
  
  reloadData() {
    this.list = this.service.getOffers();
  }



  deleteOffer = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.service.deleteOfferdeleteOffer(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }

  update(i: any) {
    console.log('update', i)
    this.router.navigate(['/UpdateRentalOffer',i.offreid])
  }

  detail(data:any){
    console.log(data)
    const url = 'listeContratFront/'+data.offreid
    this.router.navigateByUrl(url)
  }

  disponile(data:any){
    console.log(data.offreid)
    this.service.getDisponible(data.offreid).subscribe(res=>{
      console.log(res)
      alert(res)
    },err=>{
      alert('error de serveur')
    })
  }

  getAvailableOffers(){
    const date1 = new DatePipe('en-US').transform(this.datePickerForm.value.startdate, 'dd-MM-yyyy');
    console.log(date1)
    const date2 = new DatePipe('en-US').transform(this.datePickerForm.value.enddate, 'dd-MM-yyyy');
    console.log(date2)

    this.service.getAvailableOffers(date1, date2).subscribe(
      (res) => {
        this.list = res;
        console.log(res);
      }

    )
  }

  filterD(){
    this.list = this.list.sort(function(a:any, b:any){
      return b.price - a.price
    })
  }
  filterA(){
    this.list = this.list.sort(function(a:any, b:any){
      return a.price - b.price
    })
  }


}
