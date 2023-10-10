import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Offer } from 'src/app/modele/offrePlan/offre.modele';
import { ServicePlanService } from 'src/app/shared/service-plan.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listeplan',
  templateUrl: './listeplan.component.html',
  styleUrls: ['./listeplan.component.css']
})
export class ListeplanComponent implements OnInit {
  offers!: Observable<Offer[]>;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
value!:any;

  constructor(private service :ServicePlanService, private router : Router
    ) { }

  ngOnInit(): void {
    this.service.getOffers().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
   
      }
      
          )
  }
  reloadData() {
    this.list = this.service.getOffers();
  }

  deleteOffer = (id: number) => {
    this.alertConfirmation().then((result) => {
      if (result.value) {
        this.service.deleteOfferdeleteOffer(id).subscribe(() => {
          // Recharge la page après la suppression
          window.location.reload();
        });
      }
    });
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
    this.router.navigate(['/updateplan',i.planid])
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
 
  
  detail(data:any){
    console.log(data)
    const url = 'listeContratPlan/'+data.planid
    this.router.navigateByUrl(url)
  }
  alertConfirmation() {
    return Swal.fire({
      title: 'Are you sure?',
      text: 'This process is irreversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, go ahead.',
      cancelButtonText: 'No, let me think',
    });
  }
}
