import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/modele/Payment/payment.modele';
import { PaymentService } from 'src/app/shared/payment.service';

@Component({
  selector: 'app-payement',
  templateUrl: './payement.component.html',
  styleUrls: ['./payement.component.css']
})
export class PayementComponent implements OnInit {
  list!: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  constructor(private service : PaymentService) { }

  ngOnInit(): void {
    this.service.getAllPayments().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
  })
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